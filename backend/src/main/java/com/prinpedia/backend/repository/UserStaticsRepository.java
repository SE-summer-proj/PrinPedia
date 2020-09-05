package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.UserStatics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserStaticsRepository extends JpaRepository<UserStatics, Integer> {
    List<UserStatics> findByDate(Date date);

    @Query(value =
            "select new com.prinpedia.backend.entity.UserStatics(a.date, sum(a.count)) " +
                    "from UserStatics a where a.date between ?1 and ?2 " +
                    "group by a.date")
    List<UserStatics> findAllByDateBetween(Date start, Date end);

    void deleteByDate(Date date);
}
