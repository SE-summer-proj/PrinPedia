package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.EntryStatics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EntryStaticsRepository extends JpaRepository<EntryStatics, Integer> {
    EntryStatics findByTitleAndDate(String title, Date date);

    List<EntryStatics> findAllByDate(Date date);

    @Query(value =
            "select new com.prinpedia.backend.entity.EntryStatics(a.title, sum(a.count)) " +
            "from EntryStatics a where a.date between ?1 and ?2 " +
            "group by a.title ")
    List<EntryStatics> findAllByDateBetween(Date start, Date end);

    List<EntryStatics> findAllByDateBetweenAndTitle(Date start, Date end, String title);

    void deleteByTitle(String test_entry);
}
