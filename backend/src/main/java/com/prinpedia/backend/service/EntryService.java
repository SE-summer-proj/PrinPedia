package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.*;
import org.bson.types.ObjectId;

import java.util.List;

public interface EntryService {
    Entry findByTitle(String title);
    String searchTitle(String keyword);
    List<ElasticEntry> searchTitleAndSummary(String keyword);
    Boolean createEntry(String title);
    /*Boolean editEntryOld(String title, String summary, List<Content> contentList,
                      List<Section> sectionList);*/
    Boolean editEntry(String title, String wikiText);
    Boolean editEntryRequest(String title, String wikiText, String username);
    List<EntryEditRequest> getEditLogByUser(String username);
    EntryEditRequest getEditLogById(ObjectId id);
    List<EntryEditRequest> getEditLogAdmin(Boolean examined);
    Boolean examineEditLog(ObjectId id, Boolean passed);
    List<String> findParents(String title);
    List<String> findChildren(String title);
}
