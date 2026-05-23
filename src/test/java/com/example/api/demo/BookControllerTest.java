package com.example.api.demo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @WithMockUser(roles = "USER")
    class AuthenticatedUserTests {

        @Test
        void getAllBooks_shouldReturnOk() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void getBookById_shouldReturnNotFound_whenBookDoesNotExist() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books/{id}", 9999L))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void deleteBookById_shouldReturnForbidden_whenDeletingBookAsUser() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/1"))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        @Test
        void updateBookById_shouldReturnForbidden_whenUpdatingBookAsUser() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/1"))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

    }
}
