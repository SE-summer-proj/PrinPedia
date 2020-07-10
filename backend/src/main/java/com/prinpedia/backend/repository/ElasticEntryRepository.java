package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.ElasticEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticEntryRepository extends ElasticsearchRepository<ElasticEntry, Integer> {
    ElasticEntry findByEntryTitle(String science);
}
