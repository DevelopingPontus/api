package com.example.api.demo.common.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.http.HttpResponse;

@AutoConfigureMockMvc
@SpringBootTest
class SecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void postWithTokenShouldCreated() throws Exception {


        String requestBody = """
                {
                  "title": "string",
                  "author": "string",
                  "isbn": "string",
                  "publishedYear": 0
                }
                """;
        mockMvc.perform(post("/api/v1/authors").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    void postWithNoTokenShouldForbidden() throws Exception {
        String requestBody = """
                {
                  "title": "string",
                  "author": "string",
                  "isbn": "string",
                  "publishedYear": 0
                }
                """;
        mockMvc.perform(post("/api/v1/authors").contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isForbidden());
    }
}
