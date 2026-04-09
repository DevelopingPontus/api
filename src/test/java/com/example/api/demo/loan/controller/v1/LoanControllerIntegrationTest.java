package com.example.api.demo.loan.controller.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.example.api.demo.book.dto.v1.BookReq1;
import com.example.api.demo.book.dto.v1.BookRes1;
import com.example.api.demo.generic.wrappers.GenericWrapperResponse;
import com.example.api.demo.loan.dto.LoanReq1;
import com.example.api.demo.loan.dto.LoanRes1;

@SpringBootTest(classes = com.example.api.demo.DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Loan Controller Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LoanControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate;

    private String baseUrl;
    private String booksUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/loans";
        booksUrl = "http://localhost:" + port + "/api/v1/books";
        restTemplate = new RestTemplate();

    }

    @Test
    @DisplayName("Loan a book")
    void loanBook() {

        BookReq1 bookRequest = new BookReq1("Clean Code", "Robert C. Martin", "978-0132350884", 2008);

        ResponseEntity<GenericWrapperResponse<BookRes1>> response = restTemplate.exchange(
                booksUrl,
                org.springframework.http.HttpMethod.POST,
                new HttpEntity<>(bookRequest),
                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                });

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response);


        Long bookId = response.getBody().getData().get(0).id();
        LoanReq1 loanRequest = new LoanReq1(bookId);

        ResponseEntity<GenericWrapperResponse<LoanRes1>> response2 = restTemplate.exchange(
                baseUrl,
                org.springframework.http.HttpMethod.POST,
                new HttpEntity<>(loanRequest),
                new ParameterizedTypeReference<GenericWrapperResponse<LoanRes1>>() {
                });

        assertEquals(HttpStatus.CREATED, response2.getStatusCode());
        

        ResponseEntity<GenericWrapperResponse<BookRes1>> loanedBook = restTemplate.exchange(
                booksUrl+"/"+ bookId,
                org.springframework.http.HttpMethod.GET,
                new HttpEntity<>(bookId.toString()),
                new ParameterizedTypeReference<GenericWrapperResponse<BookRes1>>() {
                });

        assertFalse(
                    loanedBook.getBody().getData().get(0).available()
                );
    }

}
