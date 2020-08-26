package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.EntryInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryInfoRepository extends JpaRepository<EntryInfo, Integer> {
    EntryInfo findByTitle(String title);
}
