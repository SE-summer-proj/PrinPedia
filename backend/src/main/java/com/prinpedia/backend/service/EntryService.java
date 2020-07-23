package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.Content;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.Section;

import java.util.List;

public interface EntryService {
    Entry findByTitle(String title);
    String searchTitle(String keyword);
    List<String> searchTitleAndSummary(String keyword);
    Boolean createEntry(String title);
    /*Boolean editEntryOld(String title, String summary, List<Content> contentList,
                      List<Section> sectionList);*/
    Boolean editEntry(String title, String wikiText);
    List<String> findParents(String title);
    List<String> findChildren(String title);
}
