package com.prinpedia.backend.dao;

import com.prinpedia.backend.entity.Tag;

import java.util.Optional;

public interface TagDao {
    Optional<Tag> findById(Integer tagId);
    Tag findByKeyword(String keyword);
    void update(Tag tag);
}
