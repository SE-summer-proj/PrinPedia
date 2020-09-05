package com.prinpedia.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "entryStatics", schema = "prinpedia")
@JsonIgnoreProperties(value =
        {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "entryStaticsId")
public class EntryStatics {
    private int entryStaticsId;
    private String title;
    private Date date;
    private Long count;

    public EntryStatics(String title, Long count) {
        this.title = title;
        this.count = count;
    }

    public EntryStatics(String title, Date date, Long count) {
        this.title = title;
        this.date = date;
        this.count = count;
    }

    public EntryStatics() {}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getEntryStaticsId() {
        return entryStaticsId;
    }
    public void setEntryStaticsId(int entryStaticsId) {
        this.entryStaticsId = entryStaticsId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
}
