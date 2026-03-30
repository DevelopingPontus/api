package com.example.api.demo.book.controller.v1;

import com.example.api.demo.book.dto.v1.BookReq1;
import com.example.api.demo.book.dto.v1.BookRes1;
import com.example.api.demo.generic.wrappers.GenericWrapperResponse;
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

        @BeforeEach
        void setUp() {
                baseUrl = "http://localhost:" + port + "/api/v1/books";
                restTemplate = new RestTemplate();

        }

        // ============ GET ALL TESTS ============

        @Test
        @DisplayName("Should retrieve all books successfully")
        void testGetAllBooks() {
                ResponseEntity<GenericWrapperResponse<BookRes1>> response = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals("v1", response.getBody().getVersion());
                assertNotNull(response.getBody().getData());
        }

        @Test
        @DisplayName("Should return empty list when no books exist")
        void testGetAllBooksEmpty() {
                ResponseEntity<GenericWrapperResponse<BookRes1>> response = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertTrue(response.getBody().getData().isEmpty());
                assertEquals("v1", response.getBody().getVersion());
        }

        // ============ CREATE TESTS ============

        @Test
        @DisplayName("Should create a new book successfully")
        void testCreateBook() {
                BookReq1 bookRequest = new BookReq1("Clean Code", "Robert C. Martin", "978-0132350884", 2008);

                ResponseEntity<GenericWrapperResponse<BookRes1>> response = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertNotNull(response);
                assertEquals("v1", response.getBody().getVersion());
                assertEquals(1, response.getBody().getData().size());

                BookRes1 createdBook = response.getBody().getData().get(0);
                assertEquals("Clean Code", createdBook.title());
                assertEquals("Robert C. Martin", createdBook.author());
                assertEquals("978-0132350884", createdBook.isbn());
                assertEquals(2008, createdBook.publishedYear());
        }

        @Test
        @DisplayName("Should create multiple books in sequence")
        void testCreateMultipleBooks() {
                BookReq1 book1 = new BookReq1("The Pragmatic Programmer", "David Thomas", "978-0201616224", 1999);
                BookReq1 book2 = new BookReq1("Design Patterns", "Gang of Four", "978-0201633610", 1994);

                ResponseEntity<GenericWrapperResponse<BookRes1>> response1 = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(book1),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                ResponseEntity<GenericWrapperResponse<BookRes1>> response2 = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(book2),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                assertEquals(HttpStatus.CREATED, response1.getStatusCode());
                assertEquals(HttpStatus.CREATED, response2.getStatusCode());

                assertNotEquals(response1.getBody().getData().get(0).id(), response2.getBody().getData().get(0).id());
        }

        // ============ GET BY ID TESTS ============

        @Test
        @DisplayName("Should retrieve a book by ID successfully")
        void testGetBookById() {
                BookReq1 bookRequest = new BookReq1("Refactoring", "Martin Fowler", "978-0201485677", 1999);
                ResponseEntity<GenericWrapperResponse<BookRes1>> createResponse = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                Long bookId = createResponse.getBody().getData().get(0).id();

                ResponseEntity<GenericWrapperResponse<BookRes1>> getResponse = restTemplate.exchange(
                                baseUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                assertEquals(HttpStatus.OK, getResponse.getStatusCode());
                assertEquals(1, getResponse.getBody().getData().size());
                assertEquals("Refactoring", getResponse.getBody().getData().get(0).title());
                assertEquals("Martin Fowler", getResponse.getBody().getData().get(0).author());
        }

        // ============ DELETE TESTS ============

        @Test
        @DisplayName("Should delete a book successfully")
        void testDeleteBook() {
                BookReq1 bookRequest = new BookReq1("Test Book", "Test Author", "978-0000000000", 2020);
                ResponseEntity<GenericWrapperResponse<BookRes1>> createResponse = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                Long bookId = createResponse.getBody().getData().get(0).id();

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
}
