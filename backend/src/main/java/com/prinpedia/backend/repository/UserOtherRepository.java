package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.UserOther;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserOtherRepository extends MongoRepository<UserOther, Integer> {
    UserOther findByUserId(Integer userId);
}
