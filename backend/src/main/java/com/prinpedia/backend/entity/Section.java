package com.prinpedia.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Section {
    private String sectionTitle;
    private String sectionText;

    public String getSectionTitle() {
        return sectionTitle;
    }
    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionText() {
        return sectionText;
    }
    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }
}
