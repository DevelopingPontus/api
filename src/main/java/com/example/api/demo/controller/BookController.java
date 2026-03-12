package com.example.api.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
import com.example.api.demo.exception.BookNotFoundException;
import com.example.api.demo.exception.BooksNotFoundException;
import com.example.api.demo.exception.ErrorSavingException;
import com.example.api.demo.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

// Spring
@RestController
@RequestMapping("api/v1/books")
// OpenApi
@Tag(name = "Books", description = "Operations related to books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // Spring
    @GetMapping
    // OpenApi
    @Operation(summary = "Get all books", description = "Returns all books")
    public ResponseEntity<List<BookResponse>> getAll() throws BooksNotFoundException {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    // Spring
    @GetMapping("/{id}")
    // OpenApi
    @Operation(summary = "Get a book by ID", description = "Returns a single book based on the provided ID")
    public ResponseEntity<BookResponse> getOne(@PathVariable Long id) throws BookNotFoundException {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    // Spring
    @PostMapping
    // OpenApi
    @Operation(summary = "Create a new book", description = "Creates a new book based on the provided details")
    public ResponseEntity<BookRequest> create(@RequestBody BookRequest request) throws ErrorSavingException {
        service.create(request);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }


}
