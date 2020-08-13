package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.UserStatics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserStaticsRepository extends JpaRepository<UserStatics, Integer> {
    UserStatics findByDate(Date date);

    List<UserStatics> findAllByDateBetween(Date start, Date end);
}
