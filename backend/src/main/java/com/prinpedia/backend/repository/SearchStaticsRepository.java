package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.SearchStatics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SearchStaticsRepository extends JpaRepository<SearchStatics, Integer> {
    List<SearchStatics> findByDate(Date date);

    @Query(value =
            "select new com.prinpedia.backend.entity.SearchStatics(a.date, sum(a.count)) " +
                    "from SearchStatics a where a.date between ?1 and ?2 " +
                    "group by a.date")
    List<SearchStatics> findAllByDateBetween(Date start, Date end);

    void deleteByDate(Date date);
}
