package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    Collection findByUsernameAndTitle(String username, String title);

    List<Collection> findAllByUsername(String username);
}
