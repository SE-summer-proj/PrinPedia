package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;

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
    @Order(0)
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

    @DisplayName("Find user details")
    @Test
    @WithMockUser(username = "test")
    public void findUserDetails() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"test\", " +
                                "\"mailAddr\": \"123!123.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resultStr = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user/detail")
                        .param("username", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultStr = result.getResponse().getContentAsString();
        System.out.println("Find user details result is: " + resultStr);
        jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");
    }

    @DisplayName("Find user details failure")
    @Test
    @WithMockUser(username = "test")
    public void findUserDetailsFail() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/user/detail")
                        .param("username", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultStr = result.getResponse().getContentAsString();
        System.out.println("Find user details result is: " + resultStr);
        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/detail")
                .param("username", "other"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    @DisplayName("Edit user details")
    @Test
    @WithMockUser(username = "test")
    public void editUserDetails() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"test\", " +
                                "\"mailAddr\": \"123@123.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", " +
                                "\"email\": \"456@456.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");
    }

    @DisplayName("Editing user details fails")
    @Test
    @WithMockUser(username = "wrong")
    public void editUserDetailsFail() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"wrong\", " +
                                "\"email\": \"456@456.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(-1, resultJSON.getInteger("status"),
                "Status don't match");
    }
}