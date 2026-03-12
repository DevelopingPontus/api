package com.example.api.demo.controller;

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

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
import com.example.api.demo.entity.Book;
import com.example.api.demo.service.BookService;
import com.example.api.demo.service.mapper.BookMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// Spring
@RestController
@RequestMapping("api/v1/books")
// OpenApi
@Tag(name = "Books", description = "Operations related to books")
public class BookController {
    private final BookService service;
    private final BookMapper mapper;

    public BookController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // Spring
    @GetMapping
    // OpenApi
    @Operation(summary = "Get all books", description = "Returns all books")
    public ResponseEntity<List<BookResponse>> getAll() {
        List<BookResponse> res = service.findAll().stream().map(mapper::toDto).toList();
        return ResponseEntity.ok(res);
    }

    // Spring
    @GetMapping("/{id}")
    // OpenApi
    @Operation(summary = "Get a book by ID", description = "Returns a single book based on the provided ID")
    public ResponseEntity<BookResponse> findById(@PathVariable @Valid Long id) {
        BookResponse res = mapper.toDto(service.findById(id));
        return ResponseEntity.ok(res);
    }

    // Spring
    @PostMapping
    // OpenApi
    @Operation(summary = "Create a new book", description = "Creates a new book based on the provided details")
    public ResponseEntity<BookResponse> create(@RequestBody @Valid BookRequest req) throws URISyntaxException {
        Book book = service.save(mapper.toEntity(req));
        BookResponse res = mapper.toDto(book);
        return ResponseEntity.created(new URI("/api/books/" + res.id())).body(res);
    }

}
