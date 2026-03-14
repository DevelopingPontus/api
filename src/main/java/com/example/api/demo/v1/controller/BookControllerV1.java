package com.example.api.demo.v1.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.entity.Book;
import com.example.api.demo.v1.dto.BookRequestV1;
import com.example.api.demo.v1.dto.BookResponseV1;
import com.example.api.demo.v1.service.BookServiceV1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Spring
@RestController
@RequestMapping("api/v1/books")
// OpenApi
@Tag(name = "Books", description = "Operations related to books")
public class BookControllerV1 {
    private final BookServiceV1 service;

    public BookControllerV1(BookServiceV1 service) {
        this.service = service;
    }

    // Spring
    @GetMapping
    // OpenApi
    @Operation(summary = "Get all books", description = "Returns all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<BookResponseV1>> getAll() {
        List<BookResponseV1> res = service.findAll().stream().map(this::toDto).toList();
        return ResponseEntity.ok(res);
    }

    // Spring
    @GetMapping("/{id}")
    // OpenApi
    @Operation(summary = "Get a book by ID", description = "Returns a single book based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
            @ApiResponse(responseCode = "404", description = "Book not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BookResponseV1> findById(@PathVariable @Valid Long id) {
        BookResponseV1 res = toDto(service.findById(id));
        return ResponseEntity.ok(res);
    }

    // Spring
    @PostMapping
    // OpenApi
    @Operation(summary = "Create a new book", description = "Creates a new book based on the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BookResponseV1> create(@RequestBody @Valid Book req) throws URISyntaxException {
        BookResponseV1 res = toDto(service.save(req));
        return ResponseEntity.created(new URI("/api/v1/books/" + res.id())).body(res);
    }

    // Mappers
    public BookResponseV1 toDto(Book entity) {
        return new BookResponseV1(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIsbn(),
                entity.getYear());
    }

    public Book toEntity(BookRequestV1 request) {
        return new Book(
                request.id(),
                request.title(),
                request.description(),
                request.isbn(),
                request.year());
    }

}
