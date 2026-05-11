package com.example.api.demo.features.book.controller;

import com.example.api.demo.common.wrapper.GenericWrapperResponse;
import com.example.api.demo.feature.book.v1.BookRequestV1;
import com.example.api.demo.feature.book.v1.BookResponseV1;

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

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = com.example.api.demo.DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Book Controller Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerIntegrationTest {

        @LocalServerPort
        private int port;

        private RestTemplate restTemplate;

        private String baseUrl;

        ResponseEntity<GenericWrapperResponse<BookResponseV1>> savedBook;

        @BeforeEach
        void setUp() {
                baseUrl = "http://localhost:" + port + "/api/v1/books";
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

                BookRequestV1 bookRequest = new BookRequestV1("Clean Code", "Robert C. Martin", "978-0132350884", 2008);

                savedBook = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });
        }

        // ============ GET ALL TESTS ============

        @Test
        @DisplayName("Should retrieve all books successfully")
        void testGetAllBooks() {
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> response = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals("v1", response.getBody().getVersion());
                assertNotNull(response.getBody().getData());
        }

        // ============ CREATE TESTS ============

        @Test
        @DisplayName("Should create a new book successfully")
        void testCreateBook() {

                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());
                assertNotNull(savedBook);
                assertEquals("v1", savedBook.getBody().getVersion());
                assertEquals(1, savedBook.getBody().getData().size());

                BookResponseV1 createdBook = savedBook.getBody().getData().get(0);
                assertEquals("Clean Code", createdBook.title());
                assertEquals("Robert C. Martin", createdBook.author());
                assertEquals("978-0132350884", createdBook.isbn());
                assertEquals(2008, createdBook.publishedYear());
        }

        // ============ GET BY ID TESTS ============

        @Test
        @DisplayName("Should retrieve a book by ID successfully")
        void testGetBookById() {

                Long bookId = savedBook.getBody().getData().get(0).id();

                ResponseEntity<GenericWrapperResponse<BookResponseV1>> getResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertEquals(HttpStatus.OK, getResponse.getStatusCode());
                assertEquals(1, getResponse.getBody().getData().size());
                assertEquals("Clean Code", getResponse.getBody().getData().get(0).title());
        }

        // ============ DELETE TESTS ============

        @Test
        @DisplayName("Should delete a book successfully")
        void testDeleteBook() {

                Long bookId = savedBook.getBody().getData().get(0).id();

                ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.DELETE,
                                null,
                                Void.class);

                assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        }


        // ============ AVAILABILITY TESTS ============

        @Test
        @DisplayName("Should create book with availability true")
        void testCreateBookWithAvailabilityTrue() {
                BookResponseV1 createdBook = savedBook.getBody().getData().get(0);
                assertTrue(createdBook.available(), "Book should be available when created");
        }

}
