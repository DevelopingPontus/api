package com.example.api.demo.v2.controller;

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

import com.example.api.demo.v2.dto.BookRequestV2;
import com.example.api.demo.v2.dto.BookResponseV2;
import com.example.api.demo.v2.entity.BookV2;
import com.example.api.demo.v2.service.BookServiceV2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Spring
@RestController
@RequestMapping("api/v2/books")
// OpenApi
@Tag(name = "Books", description = "Operations related to books")
public class BookControllerV2 {
    private final BookServiceV2 service;

    public BookControllerV2(BookServiceV2 service) {
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
    public ResponseEntity<List<BookResponseV2>> getAll() {
        List<BookResponseV2> res = service.findAll().stream().map(this::toDto).toList();
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
    public ResponseEntity<BookResponseV2> findById(@PathVariable @Valid Long id) {
        BookResponseV2 res = toDto(service.findById(id));
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
    public ResponseEntity<BookResponseV2> create(@RequestBody @Valid BookV2 req) throws URISyntaxException {
        BookV2 book = service.save(req);
        BookResponseV2 res = toDto(book);
        return ResponseEntity.created(new URI("/api/books/" + res.id())).body(res);
    }

    // Mappers
    public BookResponseV2 toDto(BookV2 entity) {
        return new BookResponseV2(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIsbn(),
                entity.getYear(),
                entity.isAvailable(),
                "V2");
    }

    public BookV2 toEntity(BookRequestV2 request) {
        return new BookV2(
                request.id(),
                request.title(),
                request.description(),
                request.isbn(),
                request.year(),
                request.available());
    }
}