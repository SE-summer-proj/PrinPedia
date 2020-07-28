package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.EntryEditRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EntryEditRequestRepository extends
        MongoRepository<EntryEditRequest, ObjectId> {
    List<EntryEditRequest> findByUsername(String username);

    List<EntryEditRequest> findByStatusGreaterThan(Integer status);

    List<EntryEditRequest> findByStatus(int status);
}
