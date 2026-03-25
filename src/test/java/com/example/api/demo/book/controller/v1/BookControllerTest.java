package com.example.api.demo.book.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    // @Autowired
    // private TestRestTemplate restTemplate;

    // Example test to get a list of books
    // @Test
    // public void shouldGetAllBooks() {
    //     ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/books", String.class);

    //     assertThat(response.getStatusCode()).isEqualTo(200);
    //     assertThat(response.getBody()).isNotEmpty();
    // }

    // // Example test to create a new book
    // @Test
    // public void shouldCreateNewBook() {
    //     // Assuming BookDTO has the required properties (e.g., title, author)
    //     String requestBody = "{\"title\":\"Test Book\",\"author\":\"John Doe\"}";

    //     ResponseEntity<String> response = restTemplate.postForEntity("/api/v1/books", requestBody, String.class);

    //     assertThat(response.getStatusCode()).isEqualTo(201);
    //     assertThat(response.getBody()).isNotEmpty();
    // }

    // // Example test to get a single book by ID
    // @Test
    // public void shouldGetSingleBook() {
    //     ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/books/1", String.class);

    //     assertThat(response.getStatusCode()).isEqualTo(200);
    //     assertThat(response.getBody()).isNotEmpty();
    // }

    // // Example test to update a book by ID
    // @Test
    // public void shouldUpdateBook() {
    //     // Assuming BookDTO has the required properties (e.g., title, author)
    //     String requestBody = "{\"title\":\"Updated Test Book\",\"author\":\"Jane Doe\"}";

    //     ResponseEntity<String> response = restTemplate.exchange("/api/v1/books/1", HttpMethod.PUT,
    //             new HttpEntity<>(requestBody), String.class);

    //     assertThat(response.getStatusCode()).isEqualTo(200);
    //     assertThat(response.getBody()).isNotEmpty();
    // }

    // // Example test to delete a book by ID
    // @Test
    // public void shouldDeleteBook() {
    //     ResponseEntity<Void> response = restTemplate.exchange("/api/v1/books/1", HttpMethod.DELETE, null, Void.class);

    //     assertThat(response.getStatusCode()).isEqualTo(204);
    // }
}
