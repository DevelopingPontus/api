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
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @WithMockUser(roles = "USER")
    class AuthenticatedUserTests {
        // Books -----------------------
        @Test
        void getAllBooks_shouldReturnOk() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void getPageOfBooks_shouldReturnOk() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/books/0"))
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
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/1").content(
                    """
                            {
                            "title": "string",
                            "author": "string",
                            "isbn": "string",
                            "publishedYear": 0
                            }"""))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        // Author--------------------
        @Test
        void getAllAuthors_shouldReturnOk() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        void getAuthorById_shouldReturnNotFound_whenAuthorDoesNotExist() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors/{id}", 9999L))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void deleteAuthorById_shouldReturnForbidden_whenDeletingAuthorAsUser() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/authors/1"))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        @Test
        void updateAuthorById_shouldReturnForbidden_whenUpdatingAuthorAsUser() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/authors/1").content(
                    """
                            {
                              "name": "string"
                            }
                            """))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        // Loan------------------------------
        @Test
        void getAllLoans_shouldReturnNotFound() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/loans"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void getLoanById_shouldReturnNotFound_whenLoanDoesNotExist() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/loans/{id}", 9999L))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        void deleteLoanById_shouldReturnForbidden_whenDeletingLoanAsUser() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/loans/1"))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }

        @Test
        void updateLoanById_shouldReturnForbidden_whenUpdatingLoanAsUser() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/loans/1"))
                    .andExpect(MockMvcResultMatchers.status().isForbidden());
        }
    }

    @Nested
    @WithMockUser(roles = "ADMIN")
    class AuthenticatedAdminTests {

        @Test
        void deleteBookById_shouldReturnSuccessful_whenDeletingBookAsAdmin() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/books/1"))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }


        @Test
        void deleteLoanById_shouldReturnSuccessful_whenDeletingLoanAsAdmin() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/loans/1"))
                    .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        }

    }
}
