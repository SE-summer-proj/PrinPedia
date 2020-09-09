package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.Entry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface EntryRepository extends MongoRepository<Entry, ObjectId> {
    Optional<Entry> findByTitle(String title);

    void deleteByTitle(String title);

    Entry findTopByOrderByIndexDesc();
}
