package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.service.EntryService;
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
import java.util.List;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryDao entryDao;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Entry findByTitle(String title) {
        Optional<Entry> entryOptional = entryDao.findByTitle(title);
        return entryOptional.orElse(null);
    }

    @Override
    public String searchTitle(String keyword) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("entryTitle", keyword))
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
    public List<String> searchTitleAndSummary(String keyword) {
        BoolQueryBuilder boolBuilder = new BoolQueryBuilder()
                .should(QueryBuilders.matchQuery("entryTitle", keyword))
                .should(QueryBuilders.matchQuery("entrySummary", keyword));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolBuilder)
                .build();
        SearchHits<ElasticEntry> searchHits =
                elasticsearchRestTemplate.search(searchQuery, ElasticEntry.class);
        if(searchHits.hasSearchHits()) {
            List<String> result = new ArrayList<>();
            for(SearchHit<ElasticEntry> searchHit : searchHits) {
                result.add(searchHit.getContent().getEntryTitle());
            }
            return result;
        }
        else
            return null;
    }
}
