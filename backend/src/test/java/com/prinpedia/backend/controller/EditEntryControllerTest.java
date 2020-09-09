package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.EntryEditRequest;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryEditRequestRepository;
import com.prinpedia.backend.repository.EntryNodeRepository;
import com.prinpedia.backend.repository.EntryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
@AutoConfigureMockMvc
class EditEntryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntryEditRequestRepository entryEditRequestRepository;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @Autowired
    private EntryNodeRepository entryNodeRepository;

    @BeforeEach
    public void setUp() {
        entryRepository.deleteByTitle("Created Title");
        elasticEntryRepository.deleteByEntryTitle("Created Title");
        entryNodeRepository.deleteByTitle("Created Title");
        entryEditRequestRepository.deleteByTitle("Created Title");
    }

    @AfterEach
    public void after() {
        entryRepository.deleteByTitle("Created Title");
        elasticEntryRepository.deleteByEntryTitle("Created Title");
        entryNodeRepository.deleteByTitle("Created Title");
        entryEditRequestRepository.deleteByTitle("Created Title");
    }

    @Test
    @Transactional
    @Rollback
    @WithMockUser(username = "test", roles = {"USER", "ADMIN"})
    public void editRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"keyword\": \"Created Title\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        MvcResult result =
                mockMvc.perform(MockMvcRequestBuilders
                .post("/entry/edit/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"Created Title\", " +
                        "\"wikiText\": \"wiki\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc.perform(MockMvcRequestBuilders
                                .post("/entry/edit/request")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"wikiText\": \"wiki\"}"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc.perform(MockMvcRequestBuilders
                .post("/entry/edit/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\": \"No exist\", " +
                        "\"wikiText\": \"wiki\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc.perform(MockMvcRequestBuilders
                                .get("/entry/edit/userLog")
                                .param("username", "test"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc.perform(MockMvcRequestBuilders
                .get("/entry/edit/adminLog")
                .param("examined", "false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        List<EntryEditRequest> entryEditRequestList =
                entryEditRequestRepository.findAll();
        ObjectId id = new ObjectId();
        for(EntryEditRequest request : entryEditRequestList) {
            if(request.getTitle().equals("Created Title")) {
                id = request.getId();
                break;
            }
        }
        String idString = id.toString();

        result = mockMvc.perform(MockMvcRequestBuilders
                                .get("/entry/edit/detail")
                                .param("id", idString))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc.perform(MockMvcRequestBuilders
                                .get("/entry/edit/detail")
                                .param("id",
                                        "5f06b9d4643cd113ed90bc5a"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc.perform(MockMvcRequestBuilders
                                .post("/entry/edit/examine")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\": \"" + idString + "\", " +
                                        "\"passed\": \"false\"}"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        result = mockMvc.perform(MockMvcRequestBuilders
                .post("/entry/edit/examine")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"5f06b9d4643cd113ed90bc5a\", " +
                        "\"passed\": \"false\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(-1, jsonObject.getInteger("status"),
                "Status don't match");
    }

}