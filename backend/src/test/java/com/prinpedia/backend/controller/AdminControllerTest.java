package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
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

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getAllUsers() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/admin/allUser"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultStr = result.getResponse().getContentAsString();
        System.out.println("Get all users result is: " + resultStr);
    }

    @Test
    @WithMockUser(username = "admin")
    public void getAllUsersFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/admin/allUser"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void grantAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", \"password\": \"test\", " +
                        "\"mailAddr\": \"123@123.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/admin/grant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/admin/grant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"wrong\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(-1, resultJSON.getInteger("status"),
                "Status don't match");
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void changeUserAbility() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"test\", \"password\": \"test\", " +
                        "\"mailAddr\": \"123@123.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/admin/ability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"test\", " +
                                "\"enabled\": \"false\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/admin/ability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"wrong\", " +
                                "\"enabled\": \"false\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(-1, resultJSON.getInteger("status"),
                "Status don't match");
    }

}