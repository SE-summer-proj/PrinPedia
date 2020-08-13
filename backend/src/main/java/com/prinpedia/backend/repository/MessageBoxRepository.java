package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.MessageBox;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageBoxRepository extends MongoRepository<MessageBox, ObjectId> {
    List<MessageBox> findByUsername(String username);

    List<MessageBox> findByReplied(Boolean isReplied);
}
