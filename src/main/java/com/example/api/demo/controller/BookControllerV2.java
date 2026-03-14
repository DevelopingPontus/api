package com.example.api.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.BookRequestV2;
import com.example.api.demo.dto.BookResponseV2;
import com.example.api.demo.entity.Book;
import com.example.api.demo.entity.BookStatus;
import com.example.api.demo.exception.BookNotFoundException;
import com.example.api.demo.service.BookServiceV2;

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

    // // Spring
    // @GetMapping
    // // OpenApi
    // @Operation(summary = "Get all books", description = "Returns all books")
    // @ApiResponses(value = {
    //         @ApiResponse(responseCode = "200", description = "Successfully retrieved all books"),
    //         @ApiResponse(responseCode = "500", description = "Internal server error")
    // })
    // public ResponseEntity<List<BookStatus>> getAll() {
    //     return ResponseEntity.ok(service.findAll());
    // }

    // // Spring
    // @GetMapping("/{id}")
    // // OpenApi
    // @Operation(summary = "Get a book by ID", description = "Returns a single book based on the provided ID")
    // @ApiResponses(value = {
    //         @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
    //         @ApiResponse(responseCode = "404", description = "Book not found with the given ID"),
    //         @ApiResponse(responseCode = "500", description = "Internal server error")
    // })
    // public ResponseEntity<BookResponseV2> findById(@PathVariable @Valid Long id) {
    //     Set<Object> entity = service.findById(id);
    //     BookResponseV2 res = toDto(entity);
    //     return ResponseEntity.ok(res);
    // }

    // // Spring
    // @PostMapping
    // // OpenApi
    // @Operation(summary = "Create a new book", description = "Creates a new book based on the provided details")
    // @ApiResponses(value = {
    //         @ApiResponse(responseCode = "201", description = "Book created successfully"),
    //         @ApiResponse(responseCode = "400", description = "Invalid input provided"),
    //         @ApiResponse(responseCode = "500", description = "Internal server error")
    // })
    // public ResponseEntity<BookResponseV2> create(@RequestBody @Valid Book req) throws URISyntaxException {
    //     // Set of (Book, BookStatus)
    //     Set<Object> entity = service.save(req);
    //     BookResponseV2 res = toDto(entity);
    //     return ResponseEntity.created(new URI("/api/v2/books/" + res.id())).body(res);
    // }

    // Spring
    @PostMapping
    // OpenApi
    @Operation(summary = "Create test", description = "Creates a new book based on the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BookStatus> createTest(@RequestBody @Valid Book req) {
        return new ResponseEntity<>(service.save(req), HttpStatus.CREATED);
    }


    // Mappers
    public BookResponseV2 toDto(Set<Object> entity) {
        Book book = null;
        BookStatus bookStatus = null;

        for (Object obj : entity) {
            book = (Book) obj;
            bookStatus = (BookStatus) obj;
        }

        return new BookResponseV2(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getIsbn(),
                book.getYear(),
                bookStatus.isAvailable());
    }

    public Book toEntity(BookRequestV2 request) {
        return new Book(
                request.id(),
                request.title(),
                request.description(),
                request.isbn(),
                request.year());
    }
}