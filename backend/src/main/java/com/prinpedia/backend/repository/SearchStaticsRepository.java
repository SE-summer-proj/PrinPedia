package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.SearchStatics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SearchStaticsRepository extends JpaRepository<SearchStatics, Integer> {
    SearchStatics findByDate(Date date);

    List<SearchStatics> findAllByDateBetween(Date start, Date end);

    void deleteByDate(Date date);
}
