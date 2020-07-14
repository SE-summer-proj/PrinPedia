package com.prinpedia.backend.controller;

import org.junit.jupiter.api.DisplayName;
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
class EntryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Get entry details")
    @Test
    public void getEntryDetail() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/entry")
                        .param("entryName", "Science"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("response is:" + result.getResponse().getContentAsString());
    }
}