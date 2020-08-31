package com.prinpedia.backend.serviceImpl;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.dao.EntryRelationDao;
import com.prinpedia.backend.entity.*;
import com.prinpedia.backend.repository.EntryEditRequestRepository;
import com.prinpedia.backend.service.EntryService;
import org.bson.types.ObjectId;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryDao entryDao;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private EntryRelationDao entryRelationDao;

    @Autowired
    private EntryEditRequestRepository entryEditRequestRepository;

    private Logger logger = LoggerFactory.getLogger(EntryServiceImpl.class);

    @Override
    public Entry findByTitle(String title) {
        Optional<Entry> entryOptional = entryDao.findByTitle(title);
        return entryOptional.orElse(null);
    }

    @Override
    public String searchTitle(String keyword) {
        logger.info("Start search title with keyword: " + keyword);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("entryTitle.keyword",
                        keyword))
                .build();
        SearchHit<ElasticEntry> searchHit =
                elasticsearchRestTemplate
                        .searchOne(searchQuery, ElasticEntry.class);
        logger.info("Search finished with keyword: " + keyword);

        if(searchHit != null) {
            return searchHit.getContent().getEntryTitle();
        }
        else {
            return null;
        }
    }

    @Override
    public List<ElasticEntry> searchTitleAndSummary(String keyword, Integer page) {
        logger.info("Start search title and summary with keyword: " + keyword);

        List<Term> termList = IndexTokenizer.segment(keyword);
        logger.info("Keyword(" + keyword + ") segmentation finished");

        BoolQueryBuilder boolBuilder = new BoolQueryBuilder();
        for(Term term : termList) {
            boolBuilder
                    .should(QueryBuilders
                            .matchQuery("entryTitle", term.word).boost(2))
                    .should(QueryBuilders
                            .matchQuery("entrySummary", term.word));
        }
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolBuilder)
                .build();
        Pageable pageable = PageRequest.of(page, 20);
        searchQuery.setPageable(pageable);
        SearchHits<ElasticEntry> searchHits =
                elasticsearchRestTemplate.search(searchQuery, ElasticEntry.class);
        logger.info("Search title and summary finished with keyword: " + keyword
                + ".Total hits: " + searchHits.getTotalHits());

        if(searchHits.hasSearchHits()) {
            List<ElasticEntry> result = new ArrayList<>();
            for(SearchHit<ElasticEntry> searchHit : searchHits) {
                result.add(searchHit.getContent());
            }
            logger.info("Search result size: "+ result.size());
            return result;
        }
        else
            return null;
    }

    @Override
    public List<ElasticEntry> advancedSearch(String must, String should,
                                             String mustNot, String mustTotal,
                                             String mustTitle, Integer page) {
        logger.info("Start advanced search");
        BoolQueryBuilder boolBuilder = new BoolQueryBuilder();
        List<Term> termList;
        if(must != null) {
            termList = IndexTokenizer.segment(must);
            for (Term term : termList) {
                boolBuilder
                        .must(QueryBuilders
                                .matchQuery("entrySummary", term.word));
            }
        }

        if(should != null) {
            termList = IndexTokenizer.segment(should);
            for (Term term : termList) {
                boolBuilder
                        .should(QueryBuilders
                                .matchQuery("entryTitle", term.word)
                                .boost(2))
                        .should(QueryBuilders
                                .matchQuery("entrySummary", term.word));
            }
        }

        if(mustNot != null) {
            termList = IndexTokenizer.segment(mustNot);
            for (Term term : termList) {
                boolBuilder
                        .mustNot(QueryBuilders
                                .matchQuery("entryTitle", term.word))
                        .mustNot(QueryBuilders
                                .matchQuery("entrySummary", term.word));
            }
        }

        if(mustTitle != null) {
            termList = IndexTokenizer.segment(mustTitle);
            for (Term term : termList) {
                boolBuilder
                        .must(QueryBuilders
                                .matchQuery("entryTitle", term.word));
            }
        }

        if(mustTotal != null) {
            boolBuilder
                    .must(QueryBuilders
                            .matchQuery("entrySummary", mustTotal));
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolBuilder)
                .build();
        Pageable pageable = PageRequest.of(page, 20);
        searchQuery.setPageable(pageable);
        SearchHits<ElasticEntry> searchHits =
                elasticsearchRestTemplate.search(searchQuery, ElasticEntry.class);
        logger.info("Advanced search finished. Total hits: " +
                searchHits.getTotalHits());

        if(searchHits.hasSearchHits()) {
            List<ElasticEntry> result = new ArrayList<>();
            for(SearchHit<ElasticEntry> searchHit : searchHits) {
                result.add(searchHit.getContent());
            }
            logger.info("Search result size: "+ result.size());
            return result;
        }
        else
            return null;
    }

    @Override
    public Boolean createEntry(String title) {
        if(entryDao.findByTitle(title).isPresent()) return false;
        Entry entry = new Entry();
        entry.setTitle(title);
        return entryDao.create(entry);
    }

    @Override
    public Boolean lockEntry(String title, Boolean lock) {
        Entry entry = findByTitle(title);
        if(entry == null) return false;
        entry.setLocked(lock);
        entryDao.update(entry);
        return true;
    }

    @Override
    public Boolean editEntry(String title, String wikiText) {
        Entry entry = findByTitle(title);
        if(entry == null) {
            entry = new Entry();
            entry.setTitle(title);
        }
        entry.setWikiText(wikiText);
        return entryDao.update(entry);
    }

    @Override
    public Boolean editEntryRequest(String title, String wikiText,
                                    String username) {
        EntryEditRequest entryEditRequest = new EntryEditRequest();
        entryEditRequest.setTitle(title);
        entryEditRequest.setWikiText(wikiText);
        entryEditRequest.setUsername(username);
        entryEditRequest.setDate(new Date());
        entryEditRequest.setStatus(0);
        entryEditRequestRepository.save(entryEditRequest);
        return true;
    }

    @Override
    public List<EntryEditRequest> getEditLogByUser(String username) {
        return entryEditRequestRepository.findByUsername(username);
    }

    @Override
    public EntryEditRequest getEditLogById(ObjectId id) {
        Optional<EntryEditRequest> request =
                entryEditRequestRepository.findById(id);
        return request.orElse(null);
    }

    @Override
    public List<EntryEditRequest> getEditLogAdmin(Boolean examined) {
        if(examined) {
            return entryEditRequestRepository.findByStatusGreaterThan(0);
        }
        else {
            return entryEditRequestRepository.findByStatus(0);
        }
    }

    @Override
    public Boolean examineEditLog(ObjectId id, Boolean passed) {
        Optional<EntryEditRequest> request =
                entryEditRequestRepository.findById(id);
        if(request.isEmpty()) return false;
        EntryEditRequest entryEditRequest = request.get();
        if(entryEditRequest.getStatus() != 0) return false;
        if(passed) {
            entryEditRequest.setStatus(1);
            editEntry(entryEditRequest.getTitle(),
                    entryEditRequest.getWikiText());
        }
        else entryEditRequest.setStatus(2);
        entryEditRequestRepository.save(entryEditRequest);
        return true;
    }

    @Override
    public List<String> findParents(String title) {
        List<EntryNode> entryNodeList = entryRelationDao.findParents(title);
        List<String> result = new ArrayList<>();
        for(EntryNode entryNode : entryNodeList) {
            result.add(entryNode.getTitle());
        }
        return result;
    }

    @Override
    public List<String> findChildren(String title) {
        List<EntryNode> entryNodeList = entryRelationDao.findChildren(title);
        List<String> result = new ArrayList<>();
        for(EntryNode entryNode : entryNodeList) {
            result.add(entryNode.getTitle());
        }
        return result;
    }
}
