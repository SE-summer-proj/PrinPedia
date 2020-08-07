package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.EntryStatics;
import com.prinpedia.backend.entity.SearchStatics;
import com.prinpedia.backend.entity.UserStatics;

import java.util.Date;
import java.util.List;

public interface StaticService {
    void entryRecord(String title);
    void searchRecord();
    void userRecord();
    List<EntryStatics> getEntryStaticsByTitle(Date start, Date end, String title);
    List<EntryStatics> getEntryStatics(Date start, Date end);
    List<SearchStatics> getSearchStatics(Date start, Date end);
    List<UserStatics> getUserStatics(Date start, Date end);
}
