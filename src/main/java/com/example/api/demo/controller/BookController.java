package com.example.api.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
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
    public List<BookResponse> getAll() {
        create(new BookRequest(
               null,
                       "The Great Gatsby",
                               " A novel by F. Scott Fitzgerald",
                                       "1925",
                                               1212
        ));
        return service.getAll();
    }

    // Spring
    @GetMapping("/{id}")
    // OpenApi
    @Operation(summary = "Get a book by ID", description = "Returns a single book based on the provided ID")
    public BookResponse getOne(@PathVariable Long id) throws Exception {
            return service.get(id);
    }

    // Spring
    @PostMapping
    // OpenApi
    @Operation(summary = "Create a new book", description = "Creates a new book based on the provided details")
    public BookRequest create(@RequestBody BookRequest request) {
            service.create(request);
            return request;
    }

}
