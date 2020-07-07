package com.prinpedia.backend.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "tag")
public class Tag {
    private ObjectId tagId;
    private String keyword;
    private List<Integer> childId;
    private List<Integer> s1contentId;
    private List<Integer> s2contentId;
    private List<Integer> s3contentId;
    private List<Integer> s4contentId;

    @Id
    public ObjectId getTagId() {
        return this.tagId;
    }
    public void setTagId(ObjectId tagId) {
        this.tagId = tagId;
    }

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getChildId() {
        return childId;
    }
    public void setChildId(List<Integer> childId) {
        this.childId = childId;
    }

    public List<Integer> getS1contentId() {
        return s1contentId;
    }
    public void setS1contentId(List<Integer> s1contentId) {
        this.s1contentId = s1contentId;
    }

    public List<Integer> getS2contentId() {
        return s2contentId;
    }
    public void setS2contentId(List<Integer> s2contentId) {
        this.s2contentId = s2contentId;
    }

    public List<Integer> getS3contentId() {
        return s3contentId;
    }
    public void setS3contentId(List<Integer> s3contentId) {
        this.s3contentId = s3contentId;
    }

    public List<Integer> getS4contentId() {
        return s4contentId;
    }
    public void setS4contentId(List<Integer> s4contentId) {
        this.s4contentId = s4contentId;
    }
}
