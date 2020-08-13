package com.prinpedia.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "searchStatics", schema = "prinpedia")
@JsonIgnoreProperties(value =
        {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "searchStaticsId")
public class SearchStatics {
    private int searchStaticsId;
    private Date date;
    private long count;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getSearchStaticsId() {
        return searchStaticsId;
    }
    public void setSearchStaticsId(int searchStaticsId) {
        this.searchStaticsId = searchStaticsId;
    }

    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
}
