package com.example.api.demo.author.controller.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.example.api.demo.author.dto.AuthorRes1;
import com.example.api.demo.book.dto.v1.BookReq1;
import com.example.api.demo.book.dto.v1.BookRes1;
import com.example.api.demo.generic.wrappers.GenericWrapperResponse;

@SpringBootTest(classes = com.example.api.demo.DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Author Controller Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorControllerIntegrationTest {

        @LocalServerPort
        private int port;

        private RestTemplate restTemplate;

        private String baseUrl;
        private String booksUrl;

        @BeforeEach
        void setUp() {
                baseUrl = "http://localhost:" + port + "/api/v1/authors";
                booksUrl = "http://localhost:" + port + "/api/v1/books";
                restTemplate = new RestTemplate();
        }

        @Test
        @DisplayName("Should create Author when Book is created")
        void shouldCreateAuthorWhenBookIsCreated() {
                BookReq1 bookRequest = new BookReq1("Clean Code", "Robert C. Martin", "978-0132350884", 2008);

                ResponseEntity<GenericWrapperResponse<BookRes1>> response = restTemplate.exchange(
                                booksUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                ResponseEntity<GenericWrapperResponse<AuthorRes1>> authorResponse = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<AuthorRes1>>() {
                                });

                assertEquals(HttpStatus.OK, authorResponse.getStatusCode());
                assertNotNull(authorResponse.getBody());
                assertEquals("v1", authorResponse.getBody().getVersion());
                assertNotNull(authorResponse.getBody().getData());

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertNotNull(response);
                assertEquals("v1", response.getBody().getVersion());
                assertEquals(1, response.getBody().getData().size());
        }

        @Test
        @DisplayName("Should create Author when Book is created")
        void shouldBeRelatedWhenCreated() {
                BookReq1 bookRequest = new BookReq1("Clean Code", "Robert C. Martin", "978-0132350884", 2008);

                ResponseEntity<GenericWrapperResponse<BookRes1>> response = restTemplate.exchange(
                                booksUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                ResponseEntity<GenericWrapperResponse<AuthorRes1>> authorResponse = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<AuthorRes1>>() {
                                });

                assertEquals(HttpStatus.OK, authorResponse.getStatusCode());
                assertNotNull(authorResponse.getBody().getData().get(0).books());
                assertEquals("v1", authorResponse.getBody().getVersion());
                assertNotNull(authorResponse.getBody().getData());

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertNotNull(response);
                assertEquals("v1", response.getBody().getVersion());
                assertEquals(1, response.getBody().getData().size());
        }

}
