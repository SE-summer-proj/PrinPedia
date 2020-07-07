package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.TagDao;
import com.prinpedia.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;

    @Override
    public List<String> processSearch(String searchText) {
        List<String> stringList = new ArrayList<>();
        stringList.add(searchText);
        return stringList;
    }

    @Override
    public List<String> findTitles(List<String> keywordList) {
        return keywordList;
    }
}
