package com.example.api.demo.controller.v2;

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

import com.example.api.demo.dto.v2.BookRequest2;
import com.example.api.demo.dto.v2.BookResponse2;
import com.example.api.demo.dto.v2.ResponseWrapper;
import com.example.api.demo.entity.Book;
import com.example.api.demo.service.BookService;

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
public class BookController2 {
    private final BookService service;

    public BookController2(BookService service) {
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
    public ResponseEntity<List<BookResponse2>> getAll() {
        List<BookResponse2> res = service.findAll().stream().map(this::toDto).toList();
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
    public ResponseEntity<BookResponse2> findById(@PathVariable @Valid Long id) {
        BookResponse2 res = toDto(service.findById(id));
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
    public ResponseEntity<ResponseWrapper<BookResponse2>> create(@RequestBody @Valid BookRequest2 req)
            throws URISyntaxException {
        Book book = service.save(toEntity(req));
        BookResponse2 res = toDto(book);
        return ResponseEntity.created(new URI("/api/books/" + res.id())).body(new ResponseWrapper<>(res, 2));
    }

    // Mappers
    public BookResponse2 toDto(Book entity) {
        return new BookResponse2(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIsbn(),
                entity.getYear(),
                entity.isAvailable());
    }

    public Book toEntity(BookRequest2 request) {
        return new Book(
                request.id(),
                request.title(),
                request.description(),
                request.isbn(),
                request.year(),
                request.available());
    }

}
