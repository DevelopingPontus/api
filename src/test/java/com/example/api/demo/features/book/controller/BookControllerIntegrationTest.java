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

import java.util.List;

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

                BookRequestV1 bookRequest = new BookRequestV1("Clean Code", "Robert C. Martin", "978-0132350884", 2008,
                                true);

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

        @Test
        @DisplayName("Should return 404 when trying to retrieve a non-existent book by ID")
        void testGetNonExsistingBookById() {

                Long bookId = 9999L;

                ResponseEntity<GenericWrapperResponse<BookResponseV1>> getResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
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

        @Test
        @DisplayName("Should return 204 when deleting non-existent book")
        void testDeleteNonExistentBook() {
                ResponseEntity<Void> response = restTemplate.exchange(
                                baseUrl + "/99999",
                                org.springframework.http.HttpMethod.DELETE,
                                null,
                                Void.class);

                assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }

        // ============ AVAILABILITY TESTS (SPLIT CACHING) ============

        @Test
        @DisplayName("Should create book with availability true")
        void testCreateBookWithAvailabilityTrue() {
                BookResponseV1 createdBook = savedBook.getBody().getData().get(0);
                assertTrue(createdBook.available(), "Book should be available when created");
        }

        @Test
        @DisplayName("Should update book availability to unavailable")
        void testUpdateBookAvailabilityToUnavailable() {
                Long bookId = savedBook.getBody().getData().get(0).id();

                // Update availability
                ResponseEntity<Void> updateResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId + "/availability?available=false",
                                org.springframework.http.HttpMethod.PUT,
                                null,
                                Void.class);

                assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

                // Verify availability changed
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> getResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertFalse(getResponse.getBody().getData().get(0).available(),
                                "Book availability should be false after update");
        }

        @Test
        @DisplayName("Should update book availability to available")
        void testUpdateBookAvailabilityToAvailable() {
                Long bookId = savedBook.getBody().getData().get(0).id();

                // First set to unavailable
                restTemplate.exchange(
                                baseUrl + "/" + bookId + "/availability?available=false",
                                org.springframework.http.HttpMethod.PUT,
                                null,
                                Void.class);

                // Then set back to available
                ResponseEntity<Void> updateResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId + "/availability?available=true",
                                org.springframework.http.HttpMethod.PUT,
                                null,
                                Void.class);

                assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

                // Verify availability changed
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> getResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertTrue(getResponse.getBody().getData().get(0).available(),
                                "Book availability should be true after update");
        }

        @Test
        @DisplayName("Should handle multiple availability updates")
        void testMultipleAvailabilityUpdates() {
                Long bookId = savedBook.getBody().getData().get(0).id();

                // Toggle availability multiple times
                for (int i = 0; i < 3; i++) {
                        boolean shouldBeAvailable = i % 2 == 0;
                        ResponseEntity<Void> updateResponse = restTemplate.exchange(
                                        baseUrl + "/" + bookId + "/availability?available=" + shouldBeAvailable,
                                        org.springframework.http.HttpMethod.PUT,
                                        null,
                                        Void.class);

                        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

                        ResponseEntity<GenericWrapperResponse<BookResponseV1>> getResponse = restTemplate.exchange(
                                        baseUrl + "/" + bookId,
                                        org.springframework.http.HttpMethod.GET,
                                        null,
                                        new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                        });

                        assertEquals(shouldBeAvailable, getResponse.getBody().getData().get(0).available());
                }
        }

        // ============ VALIDATION TESTS ============

        @Test
        @DisplayName("Should retrieve book with all fields populated")
        void testGetBookWithAllFields() {
                Long bookId = savedBook.getBody().getData().get(0).id();

                ResponseEntity<GenericWrapperResponse<BookResponseV1>> response = restTemplate.exchange(
                                baseUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                BookResponseV1 book = response.getBody().getData().get(0);

                assertNotNull(book.id());
                assertNotNull(book.title());
                assertNotNull(book.author());
                assertNotNull(book.isbn());
                assertTrue(book.publishedYear() > 0);
        }

        @Test
        @DisplayName("Should get all books including newly created ones")
        void testGetAllBooksIncludesNewBooks() {
                // Create additional book
                List<BookRequestV1> newBook = List.of(
                                new BookRequestV1("Effective Java", "Joshua Bloch", "978-0134685991", 2017, true));

                restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(newBook),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                // Get all books
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> response = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertTrue(response.getBody().getData().size() >= 2,
                                "Should have at least 2 books");
        }
}
