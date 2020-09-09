package com.prinpedia.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "entryInfo", schema = "prinpedia")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "entryInfoId")
public class EntryInfo {
    private Integer entryInfoId;
    private String title;
    private List<Tag> tagList;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public Integer getEntryInfoId() {
        return entryInfoId;
    }
    public void setEntryInfoId(Integer entryInfoId) {
        this.entryInfoId = entryInfoId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tag_entry_relation",
            joinColumns = {@JoinColumn(name = "entry_info_id",
                    referencedColumnName = "entryInfoId")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id",
                    referencedColumnName = "tagId")})
    public List<Tag> getTagList() {
        return tagList;
    }
    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }
}
