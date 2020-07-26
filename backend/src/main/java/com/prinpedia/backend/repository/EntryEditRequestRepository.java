package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.EntryEditRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntryEditRequestRepository extends
        MongoRepository<EntryEditRequest, ObjectId> {

}
