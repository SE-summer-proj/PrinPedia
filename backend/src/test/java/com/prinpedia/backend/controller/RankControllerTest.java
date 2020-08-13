package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
@AutoConfigureMockMvc
class RankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Rank controller")
    @Test
    public void rankTest() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/rank"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");
    }
}