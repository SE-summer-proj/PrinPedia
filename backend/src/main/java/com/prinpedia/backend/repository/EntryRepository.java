package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryRepository extends MongoRepository<Entry, Integer> {

}
