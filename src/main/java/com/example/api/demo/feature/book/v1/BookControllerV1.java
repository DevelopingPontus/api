package com.example.api.demo.feature.book.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.common.wrapper.GenericWrapperResponse;
import com.example.api.demo.feature.book.BookFacade;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookControllerV1 {

    protected final BookFacade bookFacade;
    protected final String version;

    protected BookControllerV1(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
        this.version = "v1";
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books")
    @GetMapping
    public ResponseEntity<GenericWrapperResponse<BookResponseV1>> getAll() {
        List<BookResponseV1> entities = bookFacade.getAll();
        GenericWrapperResponse<BookResponseV1> wrapperResponse = new GenericWrapperResponse<>(entities, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Get book by it's ID", description = "Retrieve book")
    @Parameter(name = "bookId", required = true, description = "ID of the book to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the book")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/{bookId}")
    public ResponseEntity<GenericWrapperResponse<BookResponseV1>> getById(@PathVariable @PositiveOrZero Long bookId) {
        List<BookResponseV1> singleList = List.of(bookFacade.getById(bookId));
        GenericWrapperResponse<BookResponseV1> wrapperResponse = new GenericWrapperResponse<>(singleList, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Delete book by it's ID", description = "Delete a book")
    @Parameter(name = "bookId", required = true, description = "ID of the book to delete")
    @ApiResponse(responseCode = "204", description = "book deleted successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden request for users role")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteById(@PathVariable @Positive Long bookId) {
        bookFacade.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create book", description = "Create a new book")
    @ApiResponse(responseCode = "201", description = "Entity created successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden request for users role")
    @PostMapping
    public ResponseEntity<GenericWrapperResponse<BookResponseV1>> save(@RequestBody @Validated BookRequestV1 bookRequest) {
        List<BookResponseV1> savedDto = List.of(bookFacade.save(bookRequest));
        GenericWrapperResponse<BookResponseV1> wrapperResponse = new GenericWrapperResponse<>(savedDto, version);
        return ResponseEntity.status(201).body(wrapperResponse);
    }

    @Operation(summary = "Update book by Book Id", description = "Uses Book Id to update book.")
    @Parameter(name = "bookId", required = true, description = "ID of the book to return")
    @ApiResponse(responseCode = "200", description = "Entity updated successfully")
    @ApiResponse(responseCode = "403", description = "Forbidden request for users role")
    @PutMapping("/{bookId}")
    public ResponseEntity<GenericWrapperResponse<BookResponseV1>> update(@PathVariable @Positive Long bookId,
            @RequestBody @Validated BookRequestV1 bookRequest) {
        List<BookResponseV1> updatedDtos = List.of(bookFacade.update(bookId, bookRequest));
        GenericWrapperResponse<BookResponseV1> wrapperResponse = new GenericWrapperResponse<>(updatedDtos, version);
        return ResponseEntity.ok(wrapperResponse);
    }

}
