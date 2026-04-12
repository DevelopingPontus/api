package com.example.api.demo.features.loan.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.example.api.demo.features.book.dto.BookReq1;
import com.example.api.demo.features.book.dto.BookRes1;
import com.example.api.demo.common.wrappers.GenericWrapperResponse;
import com.example.api.demo.features.loan.dto.LoanReq1;
import com.example.api.demo.features.loan.dto.LoanRes1;
import com.example.api.demo.features.loan.service.LoanService;

@SpringBootTest(classes = com.example.api.demo.DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Loan Controller Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LoanControllerIntegrationTest {

        @Autowired
        LoanService loanService;

        @LocalServerPort
        private int port;

        private RestTemplate restTemplate;

        private String baseUrl;
        private String booksUrl;

        ResponseEntity<GenericWrapperResponse<BookRes1>> savedBook;

        @BeforeEach
        void setUp() {
                baseUrl = "http://localhost:" + port + "/api/v1/loans";
                booksUrl = "http://localhost:" + port + "/api/v1/books";
                restTemplate = new RestTemplate();

                List<BookReq1> bookRequest = List
                                .of(new BookReq1("Clean Code", "Robert C. Martin", "978-0132350884", 2008));

                savedBook = restTemplate.exchange(
                                booksUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });
        }

        @Test
        @DisplayName("Loan a book")
        void loanBook() {

                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                List<LoanReq1> loanRequest = List.of(new LoanReq1(bookId));

                ResponseEntity<GenericWrapperResponse<LoanRes1>> response2 = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanRes1>>() {
                                });

                assertEquals(HttpStatus.CREATED, response2.getStatusCode());

                ResponseEntity<GenericWrapperResponse<BookRes1>> loanedBook = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                new HttpEntity<>(bookId.toString()),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                assertFalse(
                                loanedBook.getBody().getData().get(0).available());
        }

        @Test
        void shouldReturnBook() {

                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                List<LoanReq1> loanRequest = List.of(new LoanReq1(bookId));

                ResponseEntity<GenericWrapperResponse<LoanRes1>> response2 = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanRes1>>() {
                                });

                assertEquals(HttpStatus.CREATED, response2.getStatusCode());

                Long loanId = response2.getBody().getData().get(0).id();

                restTemplate.exchange(
                                baseUrl + "/" + loanId,
                                org.springframework.http.HttpMethod.PUT,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanRes1>>() {
                                });

                ResponseEntity<GenericWrapperResponse<BookRes1>> loanedBook = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                new HttpEntity<>(bookId.toString()),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                                });

                assertTrue(
                                loanedBook.getBody().getData().get(0).available());
        }

}
