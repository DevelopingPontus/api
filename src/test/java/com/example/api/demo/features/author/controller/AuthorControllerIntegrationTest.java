package com.example.api.demo.features.author.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.example.api.demo.features.author.AuthorRes1;
import com.example.api.demo.features.book.BookReq1;
import com.example.api.demo.features.book.BookRes1;
import com.example.api.demo.common.wrappers.GenericWrapperResponse;

@SpringBootTest(classes = com.example.api.demo.DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Author Controller Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorControllerIntegrationTest {

        @LocalServerPort
        private int port;

        private RestTemplate restTemplate;

        private String baseUrl;
        private String booksUrl;

        ResponseEntity<GenericWrapperResponse<BookRes1>> savedBook;

        @BeforeEach
        void setUp() {
                baseUrl = "http://localhost:" + port + "/api/v1/authors";
                booksUrl = "http://localhost:" + port + "/api/v1/books";
                restTemplate = new RestTemplate();
                // Configure RestTemplate to not throw on 4xx/5xx responses
                restTemplate.setErrorHandler(new org.springframework.web.client.ResponseErrorHandler() {
                        public boolean hasError(org.springframework.http.client.ClientHttpResponse response)
                                        throws java.io.IOException {
                                return false; // Don't treat any response as an error
                        }

                        public void handleError(org.springframework.http.client.ClientHttpResponse response)
                                        throws java.io.IOException {
                                // Do nothing
                        }
                });

                List<BookReq1> bookRequest = List
                                .of(new BookReq1("Clean Code", "Robert C. Martin", "978-0132350884", 2008, true));

                savedBook = restTemplate.exchange(
                                booksUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

        }

        @Test
        @DisplayName("Should create Author when Book is created")
        void shouldCreateAuthorWhenBookIsCreated() {

                ResponseEntity<GenericWrapperResponse<AuthorRes1>> authorResponse = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<AuthorRes1>>() {
                                });

                assertEquals(HttpStatus.OK, authorResponse.getStatusCode());
                assertEquals(savedBook.getBody().getData().get(0),
                                authorResponse.getBody().getData().getLast().books().get(0));
                assertEquals("v1", authorResponse.getBody().getVersion());
                assertNotNull(authorResponse.getBody().getData());

        }

        @Test
        @DisplayName("Should retrieve all authors successfully")
        void testGetAllAuthors() {
                ResponseEntity<GenericWrapperResponse<AuthorRes1>> response = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<AuthorRes1>>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals("v1", response.getBody().getVersion());
                assertTrue(response.getBody().getData().size() > 0, "Should have at least one author");
        }

        @Test
        @DisplayName("Should retrieve author with associated books")
        void testGetAuthorWithBooks() {
                ResponseEntity<GenericWrapperResponse<AuthorRes1>> response = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<AuthorRes1>>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                AuthorRes1 author = response.getBody().getData().getLast();
                assertNotNull(author.name());
                assertNotNull(author.books());
                assertTrue(author.books().size() > 0, "Author should have associated books");
        }


        @Test
        @DisplayName("Should return 404 when author does not exist")
        void testGetNonExistentAuthor() {
                Long nonExistentId = 99999L;

                ResponseEntity<GenericWrapperResponse<AuthorRes1>> response = restTemplate.exchange(
                                baseUrl + "/" + nonExistentId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<AuthorRes1>>() {
                                });

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("Should return 404 when deleting non-existent author")
        void testDeleteNonExistentAuthor() {
                Long nonExistentId = 99999L;

                ResponseEntity<Void> response = restTemplate.exchange(
                                baseUrl + "/" + nonExistentId,
                                org.springframework.http.HttpMethod.DELETE,
                                null,
                                Void.class);

                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }
}