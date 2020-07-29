package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.repository.UserOtherRepository;
import com.prinpedia.backend.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoImplTest {
    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserOtherRepository userOtherRepository;

    @BeforeEach
    public void setup() {
        User user = userRepository.findByUsername("test");
        if(user != null) {
            userRepository.deleteById(user.getUserId());
            userOtherRepository.deleteByUserId(user.getUserId());
        }
    }

    @AfterEach
    public void after() {
        User user = userRepository.findByUsername("test");
        if(user != null) {
            userRepository.deleteById(user.getUserId());
            userOtherRepository.deleteByUserId(user.getUserId());
        }
    }

    @DisplayName("Update and find users")
    @Test
    @Transactional
    @Rollback
    public void userDaoTest() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        userDao.update(user);

        User findResult = userDao.findByName("test");
        assertNotNull(findResult, "Can't find created user");
        assertEquals("test", findResult.getUsername(),
                "Username don't match");
        assertEquals("test", findResult.getPassword(),
                "Password don't match");

        findResult = userDao.findById(findResult.getUserId());
        assertNotNull(findResult, "Can't find created user by Id");
        assertEquals("test", findResult.getUsername(),
                "Username don't match");
        assertEquals("test", findResult.getPassword(),
                "Password don't match");

        user.setAvatarBase64("base64");
        userDao.update(user);

        findResult = userDao.findByName("test");
        assertNotNull(findResult, "Can't find modified user");
        assertEquals("test", findResult.getUsername(),
                "Username don't match");
        assertEquals("test", findResult.getPassword(),
                "Password don't match");
        assertEquals("base64", findResult.getAvatarBase64(),
                "AvatarBase64 don't match");

        findResult = userDao.findById(findResult.getUserId());
        assertNotNull(findResult, "Can't find modified user by Id");
        assertEquals("test", findResult.getUsername(),
                "Username don't match");
        assertEquals("test", findResult.getPassword(),
                "Password don't match");
        assertEquals("base64", findResult.getAvatarBase64(),
                "AvatarBase64 don't match");

        user.setAvatarBase64("new base64");
        user.setPassword("pass");
        userDao.update(user);

        findResult = userDao.findByName("test");
        assertNotNull(findResult, "Can't find 2nd modified user");
        assertEquals("test", findResult.getUsername(),
                "Username don't match");
        assertEquals("pass", findResult.getPassword(),
                "Password don't match");
        assertEquals("new base64", findResult.getAvatarBase64(),
                "AvatarBase64 don't match");

        findResult = userDao.findById(findResult.getUserId());
        assertNotNull(findResult, "Can't find 2nd modified user by Id");
        assertEquals("test", findResult.getUsername(),
                "Username don't match");
        assertEquals("pass", findResult.getPassword(),
                "Password don't match");
        assertEquals("new base64", findResult.getAvatarBase64(),
                "AvatarBase64 don't match");

        List<User> userList = userDao.findAllUsers();
        assertTrue(userList.size() > 0, "User amount wrong");
    }
}