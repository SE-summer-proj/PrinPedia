package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.MessageBox;
import org.bson.types.ObjectId;

import java.util.List;

public interface MessageBoxService {
    void createMessage(String username, String content);
    List<MessageBox> getMessageByUser(String username);
    List<MessageBox> getAllMessage(Boolean isReplied);
    Boolean reply(ObjectId id, String reply);
}
