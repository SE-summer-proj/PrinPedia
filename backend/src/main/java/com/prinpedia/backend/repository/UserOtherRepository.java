package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.UserOther;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserOtherRepository extends MongoRepository<UserOther, Integer> {
    Optional<UserOther> findByUserId(Integer userId);

    void deleteByUserId(int userId);
}
