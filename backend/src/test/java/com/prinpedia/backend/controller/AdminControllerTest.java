package com.prinpedia.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void getAllUsers() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/admin/allUser"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultStr = result.getResponse().getContentAsString();
        System.out.println("Get all users result is: " + resultStr);
    }

    @Test
    @WithMockUser(username = "admin")
    public void getAllUsersFail() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/admin/allUser"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }
}