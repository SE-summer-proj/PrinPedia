package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void search() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/search")
                        .param("keyword", "数学"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Entry title matched: " +
                result.getResponse().getContentAsString());

        result = mockMvc
                .perform(MockMvcRequestBuilders.get("/search")
                        .param("keyword", "数学困难"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Suggestions: " +
                result.getResponse().getContentAsString());

        result = mockMvc
                .perform(MockMvcRequestBuilders.get("/search")
                        .param("keyword", "lalala"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("No match: " +
                result.getResponse().getContentAsString());
    }

    @Test
    public void advancedSearch() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/search/advanced")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"must\": \"数学\", " +
                                "\"page\": \"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(resultString);
        assertEquals(0, jsonObject.getInteger("status"));

        result = mockMvc
                .perform(MockMvcRequestBuilders.post("/search/advanced")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"must\": \"lalalala\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(resultString);
        assertEquals(-1, jsonObject.getInteger("status"));


    }
}