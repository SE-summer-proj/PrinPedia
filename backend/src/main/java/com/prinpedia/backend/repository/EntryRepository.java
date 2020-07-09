package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EntryRepository extends MongoRepository<Entry, Integer> {
    Optional<Entry> findByTitle(String title);
}
