package com.prinpedia.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "userStatics", schema = "prinpedia")
@JsonIgnoreProperties(value =
        {"handler","hibernateLazyInitializer","fieldHandler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userStaticsId")
public class UserStatics {
    private int userStaticsId;
    private Date date;
    private long count;

    public UserStatics(Date date, Long count) {
        this.date = date;
        this.count = count;
    }

    public UserStatics() {}

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public int getUserStaticsId() {
        return userStaticsId;
    }
    public void setUserStaticsId(int userStaticsId) {
        this.userStaticsId = userStaticsId;
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
