package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.repository.EntryInfoRepository;
import com.prinpedia.backend.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private EntryInfoRepository entryInfoRepository;

    @Test
    @WithMockUser(username = "test", roles = {"USER", "ADMIN"})
    @Transactional
    public void tagBasic() throws Exception {
        entryInfoRepository.deleteByTitle("Test entry");
        tagRepository.deleteByTagName("Test tag");

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tag/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tagName\": \"Test tag\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tag/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tagName\": \"Test tag\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(-1, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/tag/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Test entry\"," +
                                "\"tagList\": [\"Test tag\"]}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/tag/allTag"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/tag/tagName")
                        .param("tagName", "Test tag"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/tag/title")
                        .param("title", "Test entry"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        entryInfoRepository.deleteByTitle("Test entry");
        tagRepository.deleteByTagName("Test tag");
    }
}