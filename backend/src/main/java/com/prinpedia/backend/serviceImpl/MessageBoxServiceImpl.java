package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.MessageBox;
import com.prinpedia.backend.repository.MessageBoxRepository;
import com.prinpedia.backend.service.MessageBoxService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageBoxServiceImpl implements MessageBoxService {
    @Autowired
    private MessageBoxRepository messageBoxRepository;

    @Override
    public void createMessage(String username, String content) {
        MessageBox messageBox = new MessageBox();
        messageBox.setUsername(username);
        messageBox.setContent(content);
        messageBox.setReplied(false);
        messageBox.setTime(new Date());
        messageBoxRepository.save(messageBox);
    }

    @Override
    public List<MessageBox> getMessageByUser(String username) {
        return messageBoxRepository.findByUsername(username);
    }

    @Override
    public List<MessageBox> getAllMessage(Boolean isReplied) {
        return messageBoxRepository.findByReplied(isReplied);
    }

    @Override
    public Boolean reply(ObjectId id, String reply) {
        Optional<MessageBox> messageBox = messageBoxRepository.findById(id);
        if(messageBox.isEmpty()) return false;
        MessageBox repliedMessage = messageBox.get();
        if(repliedMessage.getReplied()) return false;
        repliedMessage.setReply(reply);
        repliedMessage.setReplied(true);
        messageBoxRepository.save(repliedMessage);
        return true;
    }
}
