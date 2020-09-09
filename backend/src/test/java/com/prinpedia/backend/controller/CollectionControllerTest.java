package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Collection;
import com.prinpedia.backend.repository.CollectionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
class CollectionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CollectionRepository collectionRepository;

    @BeforeEach
    void setUp() {
        Collection collection =
                collectionRepository
                        .findByUsernameAndTitle("test", "test");
        if(collection != null) {
            collectionRepository.deleteById(collection.getCollectionId());
        }
    }

    @AfterEach
    void tearDown() {
        Collection collection =
                collectionRepository
                        .findByUsernameAndTitle("test", "test");
        if(collection != null) {
            collectionRepository.deleteById(collection.getCollectionId());
        }
    }

    @Test
    @WithMockUser(username = "test")
    @Transactional
    public void collectionBasic() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/collection/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/collection/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(-1, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/collection/query")
                        .param("username", "test")
                        .param("title", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/collection/user")
                        .param("username", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/collection/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/collection/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(-1, resultJSON.getInteger("status"),
                "Status don't match");
    }
}