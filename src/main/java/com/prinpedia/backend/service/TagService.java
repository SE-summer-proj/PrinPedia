package com.prinpedia.backend.service;

import java.util.List;

public interface TagService {
    List<String> processSearch(String searchText);
    List<String> findTitles(List<String> keywordList);
}
