package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTagName(String tagName);

    void deleteByTagName(String tag);
}
