package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
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

import static org.junit.jupiter.api.Assertions.*;

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
        String resultStr = result.getResponse().getContentAsString();
        System.out.println("Register result is: " + resultStr);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"test\", " +
                                "\"mailAddr\": \"123!123.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultStr = result.getResponse().getContentAsString();
        System.out.println("Register with duplicate username: " + resultStr);
        jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("username=test&password=test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultStr = result.getResponse().getContentAsString();
        System.out.println("Login: " + resultStr);
        jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("username=test&password=wrong"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultStr = result.getResponse().getContentAsString();
        System.out.println("Login with wrong password: " + resultStr);
        jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultStr = result.getResponse().getContentAsString();
        System.out.println("Logout: " + resultStr);
        jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");
    }
}