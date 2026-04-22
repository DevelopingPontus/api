package com.example.api.demo.features.book.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.features.book.entity.Book;
import com.example.api.demo.features.book.dto.BookReq1;
import com.example.api.demo.features.book.dto.BookRes1;
import com.example.api.demo.features.book.service.BookService;
import com.example.api.demo.common.controllers.GenericController;
import com.example.api.demo.common.wrappers.GenericWrapperResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController1 extends GenericController<Book, BookReq1, BookRes1> {
    private final BookService bookService;

    @Autowired
    public BookController1(BookService service) {
        super(service, "v1");
        this.bookService = service;
    }

    @PutMapping("/{id}/availability")
    @Operation(summary = "Update book availability", description = "Update the availability status of a book. This refreshes the availability cache (5 min TTL) independently from book metadata cache.")
    @ApiResponse(responseCode = "200", description = "Availability updated successfully")
    @ApiResponse(responseCode = "404", description = "Book not found")
    public ResponseEntity<GenericWrapperResponse<BookRes1>> updateAvailability(@PathVariable Long id,
            @RequestParam boolean available) {
        bookService.updateBookAvailability(id, available);
        BookRes1 updatedBook = bookService.getById(id);
        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }
        List<BookRes1> result = List.of(updatedBook);
        GenericWrapperResponse<BookRes1> wrapperResponse = new GenericWrapperResponse<>(result, "v1");
        return ResponseEntity.ok(wrapperResponse);
    }
}
