package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.repository.EntryStaticsRepository;
import com.prinpedia.backend.serviceImpl.StaticServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
@AutoConfigureMockMvc
class StaticControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StaticServiceImpl staticService;

    @Autowired
    private EntryStaticsRepository entryStaticsRepository;

    @Test
    @WithMockUser(username = "test", roles = {"USER", "ADMIN"})
    @Transactional
    public void entryStatics() throws Exception {
        staticService.entryRecord("Test entry");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/statics/entry")
                        .param("start", "2000-01-01")
                        .param("end", "2049-12-31"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/statics/entryTitle")
                        .param("start", "2000-01-01")
                        .param("end", "2049-12-31")
                        .param("title", "Test entry"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        entryStaticsRepository.deleteByTitle("Test entry");
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER", "ADMIN"})
    @Transactional
    public void searchStatics() throws Exception {
        staticService.searchRecord();

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/statics/search")
                        .param("start", "2000-01-01")
                        .param("end", "2049-12-31"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER", "ADMIN"})
    @Transactional
    public void userStatics() throws Exception {
        staticService.userRecord();

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/statics/user")
                        .param("start", "2000-01-01")
                        .param("end", "2049-12-31"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");
    }

}