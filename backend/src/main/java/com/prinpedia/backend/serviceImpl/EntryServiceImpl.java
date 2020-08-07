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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Entry findByTitle(String title) {
        Optional<Entry> entryOptional = entryDao.findByTitle(title);
        return entryOptional.orElse(null);
    }

    @Override
    public String searchTitle(String keyword) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.termQuery("entryTitle", keyword))
                .build();
        SearchHit<ElasticEntry> searchHit =
                elasticsearchRestTemplate.searchOne(searchQuery, ElasticEntry.class);
        if(searchHit != null) {
            return searchHit.getContent().getEntryTitle();
        }
        else {
            return null;
        }
    }

    @Override
    public List<ElasticEntry> searchTitleAndSummary(String keyword) {
        List<Term> termList = IndexTokenizer.segment(keyword);
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
        SearchHits<ElasticEntry> searchHits =
                elasticsearchRestTemplate.search(searchQuery, ElasticEntry.class);
        if(searchHits.hasSearchHits()) {
            List<ElasticEntry> result = new ArrayList<>();
            for(SearchHit<ElasticEntry> searchHit : searchHits) {
                result.add(searchHit.getContent());
            }
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
