package com.prinpedia.backend.controller;

import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

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

    @DisplayName("Register and login")
    @Test
    public void registerAndLogin() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"test\", " +
                                "\"mailAddr\": \"123!123.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Register result is: " + result.getResponse()
                .getContentAsString());

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"test\", " +
                                "\"mailAddr\": \"123!123.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Register with duplicate username: " + result.getResponse()
                .getContentAsString());

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Login: " + result.getResponse()
                .getContentAsString());

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"wrong\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Login with wrong password: " + result.getResponse()
                .getContentAsString());
    }
}