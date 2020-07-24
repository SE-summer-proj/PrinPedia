package com.prinpedia.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class SearchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void search() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/search")
                        .param("keyword", "science"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("Entry title matched: " +
                result.getResponse().getContentAsString());

        result = mockMvc
                .perform(MockMvcRequestBuilders.get("/search")
                        .param("keyword", "greek"))
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
}