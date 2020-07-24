package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.repository.UserRepository;
import com.prinpedia.backend.serviceImpl.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("UserServiceImpl test")
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach()
    void setUp() {
        User test = userRepository.findByUsername("test");
        if(test != null) {
            userRepository.deleteById(test.getUserId());
        }
    }

    @AfterEach
    void after() {
        User test = userRepository.findByUsername("test");
        if(test != null) {
            userRepository.deleteById(test.getUserId());
        }
    }

    @DisplayName("User's registration and validation")
    @Test
    @Transactional
    @Rollback
    void registerAndValidate() {
        String username = "test";
        String password = "test";
        String email = "123@123.com";

        Boolean regResult = userService.register(username, password, email);
        assertTrue(regResult, "First registration failed");
        Boolean validResult = userService.validate(username, password);
        assertTrue(validResult, "Validation failed");
        regResult = userService.register(username, password, email);
        assertFalse(regResult, "Register success with duplicated username");
        validResult = userService.validate(username, password);
        assertTrue(validResult, "Validation failed");
    }

    @DisplayName("Create and find user")
    @Test
    @Transactional
    @Rollback
    void createAndFind() {
        String username = "test";
        String password = "test";
        String email = "123@123.com";

        Boolean createResult = userService.createUser(null);
        assertFalse(createResult, "Created an null user");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        createResult = userService.createUser(user);
        assertFalse(createResult, "Created an user with no password");

        user.setPassword(password);
        createResult = userService.createUser(user);
        assertTrue(createResult, "Creating user failed");

        User findResult = userService.findUserByName(username);
        assertNotNull(findResult, "Didn't create user");
        assertEquals(username, findResult.getUsername(), "User info not match");
        assertEquals(password, findResult.getPassword(), "User info not match");
        assertEquals(email, findResult.getEmail(), "User info not match");
    }
}