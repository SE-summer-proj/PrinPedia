package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.TagDao;
import com.prinpedia.backend.entity.Tag;
import com.prinpedia.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    @Autowired
    TagRepository tagRepository;

    @Override
    public Optional<Tag> findById(Integer tagId) {
        return tagRepository.findById(tagId);
    }

    @Override
    public Tag findByKeyword(String keyword) {
        return tagRepository.findByKeyword(keyword);
    }

    @Override
    public void update(Tag tag) {
        tagRepository.save(tag);
    }
}
