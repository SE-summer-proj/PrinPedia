package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.EntryInfo;
import com.prinpedia.backend.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findTagByEntry(String title);
    List<EntryInfo> findEntryByTag(String tagName);
    List<Tag> findAllTags();
    void editEntryTag(String title, List<String> tagList);
    Boolean createTag(String tagName);
}
