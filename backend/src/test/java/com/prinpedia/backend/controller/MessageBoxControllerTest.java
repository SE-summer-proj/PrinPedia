package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.repository.MessageBoxRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
class MessageBoxControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageBoxRepository messageBoxRepository;

    @BeforeEach
    void setUp() {
        messageBoxRepository.deleteByUsername("test");
    }

    @AfterEach
    void tearDown() {
        messageBoxRepository.deleteByUsername("test");
    }

    @Test
    @WithMockUser(username = "test", roles = {"USER", "ADMIN"})
    @Transactional
    public void messageBoxBasic() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/message/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"test\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultString = result.getResponse().getContentAsString();
        JSONObject resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/message/user")
                        .param("username", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");
        JSONArray extraData = resultJSON.getJSONArray("extraData");
        JSONObject message = extraData.getJSONObject(0);
        String id = message.getString("id");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/message/admin")
                        .param("isReplied", "false"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/message/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"" + id + "\"," +
                                "\"reply\": \"reply\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/message/reply")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"" + id + "\"," +
                                "\"reply\": \"reply\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(-1, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/message/user")
                        .param("username", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");

        result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/message/admin")
                        .param("isReplied", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        resultString = result.getResponse().getContentAsString();
        resultJSON = JSON.parseObject(resultString);
        assertEquals(0, resultJSON.getInteger("status"),
                "Status don't match");
    }
}