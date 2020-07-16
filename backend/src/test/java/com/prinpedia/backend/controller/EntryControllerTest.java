package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EntryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @DisplayName("Get entry details")
    @Test
    @WithMockUser(username = "test")
    public void getEntryDetail() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/entry")
                        .param("entryName", "Science"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("response is:" + result.getResponse().getContentAsString());
        JSONObject jsonObject = JSONObject.parseObject(result.getResponse().getContentAsString());
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");
    }

    @DisplayName("Create new entry")
    @Test
    public void createEntry() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"keyword\": \"Created Title\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(resultString);
        assertEquals(0, jsonObject.getInteger("status"));
        System.out.println("Create response: " + resultString);

        entryRepository.deleteByTitle("Created Title");
        elasticEntryRepository.deleteByEntryTitle("Created Title");
    }

    @DisplayName("Edit entry")
    @Test
    public void editEntry() throws Exception {
        String contentString = "{" +
            "\"title\": \"Edit test\"," +
            "\"summary\": \"This is summary of the entry\"" +
        "}";

        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseString = result.getResponse().getContentAsString();
        JSONObject jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"));

        result = mockMvc
                .perform(MockMvcRequestBuilders.get("/entry")
                        .param("entryName", "Edit test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("response is:" + result.getResponse().getContentAsString());
        jsonObject = JSONObject.parseObject(result.getResponse().getContentAsString());
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");

        contentString = "{" +
            "\"title\":\"Edit test\"," +
            "\"summary\":\"New summary\"," +
            "\"content\":[" +
            "{" +
                "\"label\":\"content1\"," +
                "\"text\":\"text1\"" +
            "}," +
            "{" +
                "\"label\":\"content2\"," +
                "\"text\":\"text2\"," +
                "\"children\":[" +
                "{" +
                    "\"label\":\"content2.1\"," +
                    "\"text\":\"text2.1\"" +
                "}," +
                "{" +
                    "\"label\": \"content2.2\"," +
                    "\"text\": \"text2.2\"," +
                    "\"children\": [" +
                    "{" +
                        "\"label\": \"content2.2.1\"," +
                        "\"text\": \"text2.2.1\"" +
                    "}]" +
                "}]" +
            "}]" +
        "}";

        result = mockMvc
                .perform(MockMvcRequestBuilders.post("/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        responseString = result.getResponse().getContentAsString();
        jsonObject = JSONObject.parseObject(responseString);
        assertEquals(0, jsonObject.getInteger("status"));

        result = mockMvc
                .perform(MockMvcRequestBuilders.get("/entry")
                        .param("entryName", "Edit test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println("response is:" + result.getResponse().getContentAsString());
        jsonObject = JSONObject.parseObject(result.getResponse().getContentAsString());
        assertEquals(0, jsonObject.getInteger("status"),
                "Status don't match");
        assertEquals("New summary",
                jsonObject.getJSONObject("extraData").getString("summary"),
                "Summary don't match");

        entryRepository.deleteByTitle("Edit test");
        elasticEntryRepository.deleteByEntryTitle("Edit test");
    }
}