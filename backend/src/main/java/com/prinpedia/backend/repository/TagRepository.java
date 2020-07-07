package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, Integer> {
    Tag findByKeyword(String keyword);
}
