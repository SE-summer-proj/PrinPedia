package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.MessageBox;
import com.prinpedia.backend.repository.MessageBoxRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
class MessageBoxServiceImplTest {
    @Autowired
    private MessageBoxServiceImpl messageBoxService;

    @Autowired
    private MessageBoxRepository messageBoxRepository;

    @BeforeEach
    void setUp() {
        messageBoxRepository.deleteByUsername("test");
    }

    @AfterEach
    void tearDown() {
        messageBoxRepository.deleteByUsername("test");
    }

    @Test
    @Transactional
    public void messageBasic() {
        messageBoxService.createMessage("test", "test");

        List<MessageBox> messageBoxes = messageBoxService
                .getAllMessage(false);
        assertTrue(messageBoxes.size() > 0,
                "Cannot get all messages");

        messageBoxes = messageBoxService
                .getMessageByUser("test");
        assertTrue(messageBoxes.size() > 0,
                "Cannot get messages by username");

        ObjectId id = new ObjectId();
        for(MessageBox message : messageBoxes) {
            if(message.getContent().equals("test")) {
                id = message.getId();
            }
        }
        Boolean result = messageBoxService.reply(id, "reply");
        assertTrue(result, "Cannot reply a message");
        result = messageBoxService.reply(id, "reply");
        assertFalse(result, "Reply an already replied message");
    }
}