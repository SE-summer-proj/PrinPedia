package com.prinpedia.backend.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "entry")
public class Entry {
    ObjectId entryId;
    List<Content> content;
    String summary;
    String title;
    List<Section> sectionList;

    @Id
    public ObjectId getEntryId() {
        return entryId;
    }
    public void setEntryId(ObjectId entryId) {
        this.entryId = entryId;
    }

    public List<Content> getContent() {
        return content;
    }
    public void setContent(List<Content> content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }
    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }
}
