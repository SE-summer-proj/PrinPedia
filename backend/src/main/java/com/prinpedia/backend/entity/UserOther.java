package com.prinpedia.backend.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "userOther")
public class UserOther {
    private ObjectId userOtherId;
    private Integer userId;
    private String avatarBase64;

    @Id
    public ObjectId getUserOtherId() {
        return userOtherId;
    }
    public void setUserOtherId(ObjectId userOtherId) {
        this.userOtherId = userOtherId;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }
    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }
}
