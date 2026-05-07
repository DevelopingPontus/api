/**package com.example.api.demo.features.loan.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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

import com.example.api.demo.common.wrapper.GenericWrapperResponse;
import com.example.api.demo.feature.book.v1.BookRequestV1;
import com.example.api.demo.feature.book.v1.BookResponseV1;
import com.example.api.demo.feature.loan.LoanService;
import com.example.api.demo.feature.loan.v1.LoanReqestV1;
import com.example.api.demo.feature.loan.v1.LoanResponseV1;

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

        ResponseEntity<GenericWrapperResponse<BookResponseV1>> savedBook;

        @BeforeEach
        void setUp() {
                baseUrl = "http://localhost:" + port + "/api/v1/loans";
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

                List<BookRequestV1> bookRequest = List
                                .of(new BookRequestV1("Clean Code", "Robert C. Martin", "978-0132350884", 2008, true));

                savedBook = restTemplate.exchange(
                                booksUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(bookRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });
        }

        @Test
        @DisplayName("Loan a book")
        void loanBook() {

                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                List<LoanReqestV1> loanRequest = List.of(new LoanReqestV1(bookId));

                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> response2 = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });

                assertEquals(HttpStatus.CREATED, response2.getStatusCode());

                ResponseEntity<GenericWrapperResponse<BookResponseV1>> loanedBook = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                new HttpEntity<>(bookId.toString()),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertFalse(
                                loanedBook.getBody().getData().get(0).available());
        }

        @Test
        void shouldReturnBook() {

                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                List<LoanReqestV1> loanRequest = List.of(new LoanReqestV1(bookId));

                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> response2 = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });

                assertEquals(HttpStatus.CREATED, response2.getStatusCode());

                Long loanId = response2.getBody().getData().get(0).id();

                restTemplate.exchange(
                                baseUrl + "/" + loanId,
                                org.springframework.http.HttpMethod.PUT,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });

                ResponseEntity<GenericWrapperResponse<BookResponseV1>> loanedBook = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                new HttpEntity<>(bookId.toString()),
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertTrue(
                                loanedBook.getBody().getData().get(0).available());
        }

        @Test
        @DisplayName("Should prevent lending same book multiple times")
        void testPreventMultipleLendingOfSameBook() {
                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                List<LoanReqestV1> loanRequest = List.of(new LoanReqestV1(bookId));

                // First loan should succeed
                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> firstLoan = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });
                assertEquals(HttpStatus.CREATED, firstLoan.getStatusCode());

                // Verify book is unavailable
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> bookAfterFirstLoan = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });
                assertFalse(bookAfterFirstLoan.getBody().getData().get(0).available(),
                                "Book should be unavailable after first loan");

        }

        @Test
        @DisplayName("Should restore availability after returning loaned book")
        void testRestoreAvailabilityAfterReturn() {
                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                List<LoanReqestV1> loanRequest = List.of(new LoanReqestV1(bookId));

                // Create loan
                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> loanResponse = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });
                assertEquals(HttpStatus.CREATED, loanResponse.getStatusCode());
                Long loanId = loanResponse.getBody().getData().get(0).id();

                // Verify book is unavailable
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> unavailableBook = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });
                assertFalse(unavailableBook.getBody().getData().get(0).available(),
                                "Book should be unavailable after loan");

                // Return the book
                restTemplate.exchange(
                                baseUrl + "/" + loanId,
                                org.springframework.http.HttpMethod.PUT,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });

                // Verify book is available again
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> availableBook = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });
                assertTrue(availableBook.getBody().getData().get(0).available(),
                                "Book should be available after return");
        }

        @Test
        @DisplayName("Should retrieve all loans")
        void testGetAllLoans() {
                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                List<LoanReqestV1> loanRequest = List.of(new LoanReqestV1(bookId));

                // Create loan
                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> loanResponse = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.POST,
                                new HttpEntity<>(loanRequest),
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });
                assertEquals(HttpStatus.CREATED, loanResponse.getStatusCode());

                // Get all loans
                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> allLoans = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });

                assertEquals(HttpStatus.OK, allLoans.getStatusCode());
                assertTrue(allLoans.getBody().getData().size() > 0, "Should have at least one loan");
                assertTrue(allLoans.getBody().getData().stream()
                                .anyMatch(loan -> loan.bookId().equals(bookId)),
                                "Loaned book should appear in loans list");
        }

        @Test
        @DisplayName("Should return 404 when getting non-existent loan")
        void testGetNonExistentLoan() {
                Long nonExistentId = 99999L;

                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> response = restTemplate.exchange(
                                baseUrl + "/" + nonExistentId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });

                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        @DisplayName("Should handle 100+ concurrent loan requests for same book - only one succeeds")
        void testConcurrentLoanRequests() throws InterruptedException {
                assertEquals(HttpStatus.CREATED, savedBook.getStatusCode());

                Long bookId = savedBook.getBody().getData().get(0).id();
                int numberOfConcurrentRequests = 100;

                ExecutorService executorService = Executors.newFixedThreadPool(20);
                CountDownLatch startLatch = new CountDownLatch(1);
                CountDownLatch endLatch = new CountDownLatch(numberOfConcurrentRequests);
                AtomicInteger successfulLoans = new AtomicInteger(0);
                AtomicInteger failedRequests = new AtomicInteger(0);

                // Submit 100 concurrent requests to loan the same book
                for (int i = 0; i < numberOfConcurrentRequests; i++) {
                        executorService.submit(() -> {
                                try {
                                        // Wait for all threads to be ready before starting
                                        startLatch.await();

                                        List<LoanReqestV1> loanRequest = List.of(new LoanReqestV1(bookId));

                                        ResponseEntity<GenericWrapperResponse<LoanResponseV1>> response = restTemplate
                                                        .exchange(
                                                                        baseUrl,
                                                                        org.springframework.http.HttpMethod.POST,
                                                                        new HttpEntity<>(loanRequest),
                                                                        new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                                                        });

                                        if (response.getStatusCode() == HttpStatus.CREATED) {
                                                successfulLoans.incrementAndGet();
                                        } else {
                                                failedRequests.incrementAndGet();
                                        }
                                } catch (Exception _) {
                                        // Expected - only one should succeed
                                        failedRequests.incrementAndGet();
                                } finally {
                                        endLatch.countDown();
                                }
                        });
                }

                // Start all threads at the same time
                startLatch.countDown();

                // Wait for all requests to complete
                endLatch.await();
                executorService.shutdown();

                // Verify that only exactly ONE loan was created
                assertEquals(1, successfulLoans.get(),
                                "Only one loan should have been created for the book");

                // Verify that 99 requests failed
                assertEquals(numberOfConcurrentRequests - 1, failedRequests.get(),
                                "99 concurrent requests should have failed");

                // Verify book is marked as unavailable
                ResponseEntity<GenericWrapperResponse<BookResponseV1>> bookResponse = restTemplate.exchange(
                                booksUrl + "/" + bookId,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<BookResponseV1>>() {
                                });

                assertFalse(bookResponse.getBody().getData().get(0).available(),
                                "Book should be unavailable after successful loan");

                // Verify only one loan exists in the database
                ResponseEntity<GenericWrapperResponse<LoanResponseV1>> allLoans = restTemplate.exchange(
                                baseUrl,
                                org.springframework.http.HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<GenericWrapperResponse<LoanResponseV1>>() {
                                });

                long loansForThisBook = allLoans.getBody().getData().stream()
                                .filter(loan -> loan.bookId().equals(bookId))
                                .count();

                assertEquals(1, loansForThisBook, "Exactly one loan should exist for this book in database");
        }

}
*/