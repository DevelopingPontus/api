package com.example.api.demo.book.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.book.dto.v1.BookDTO;
import com.example.api.demo.book.entity.Book;
import com.example.api.demo.book.mapper.BookMapper;
import com.example.api.demo.book.service.BookService;
import com.example.api.demo.generic.controllers.GenericController;



@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController extends GenericController<Book, BookDTO> {

    @Autowired
    public BookController(BookService service, BookMapper mapper) {
        super(service, mapper);
    }

    // No need to override methods if they are already defined in the
    // GenericController

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        return super.getAll();
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a book by its ID")
    @Parameter(name = "id", required = true, description = "ID of the book to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the book")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @Operation(summary = "Save a new book", description = "Create a new book")
    @ApiResponse(responseCode = "201", description = "Book created successfully")
    @PostMapping
    public ResponseEntity<BookDTO> save(@RequestBody Book book) {
        return super.save(book);
    }

    @Operation(summary = "Delete a book by ID", description = "Delete a book by its ID")
    @Parameter(name = "id", required = true, description = "ID of the book to delete")
    @ApiResponse(responseCode = "204", description = "Book deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        return super.deleteById(id);
    }
}