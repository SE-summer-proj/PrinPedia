package com.prinpedia.backend.security;


import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Request without authentication")
    @Test
    public void noAuthentication() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/recommend")
                        .param("username", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");
    }

    @DisplayName("Request with authentication")
    @Test
    @WithMockUser(username = "test")
    public void withAuthentication() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/recommend")
                        .param("username", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");
    }

    @DisplayName("Login exceptions")
    @Test
    public void loginExceptions() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("username=test&password=test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");
    }
}
