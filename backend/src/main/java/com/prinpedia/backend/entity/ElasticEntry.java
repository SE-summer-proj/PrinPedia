package com.prinpedia.backend.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Document(indexName = "entry")
public class ElasticEntry {
    private String id;
    private String entryTitle;
    private String entrySummary;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getEntryTitle() {
        return entryTitle;
    }
    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntrySummary() {
        return entrySummary;
    }
    public void setEntrySummary(String entrySummary) {
        this.entrySummary = entrySummary;
    }
}
