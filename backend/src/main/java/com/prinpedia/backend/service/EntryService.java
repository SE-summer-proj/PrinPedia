package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.Entry;

import java.util.List;

public interface EntryService {
    Entry findByTitle(String title);
    String searchTitle(String keyword);
    List<String> searchTitleAndSummary(String keyword);
    Boolean createEntry(String title);
}
