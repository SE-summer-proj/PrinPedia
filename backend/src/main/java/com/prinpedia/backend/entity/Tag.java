package com.prinpedia.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "tag", schema = "prinpedia")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "tagId")
public class Tag {
    private Integer tagId;
    private String tagName;
    private List<EntryInfo> entryInfoList;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Integer getTagId() {
        return tagId;
    }
    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @ManyToMany(mappedBy = "tagList")
    public List<EntryInfo> getEntryInfoList() {
        return entryInfoList;
    }
    public void setEntryInfoList(List<EntryInfo> entryInfoList) {
        this.entryInfoList = entryInfoList;
    }
}
