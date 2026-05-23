package com.example.api.demo.feature.book.v2;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.PositiveOrZero;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.feature.book.v1.BookResponseV1;

@RestController
@RequestMapping("/api/v2/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookControllerPageable {

    protected final BookFacadePageable bookFacade;
    protected final String version;

    protected BookControllerPageable(BookFacadePageable bookFacade) {
        this.bookFacade = bookFacade;
        this.version = "v2";
    }

    @Operation(summary = "Get a page of books", description = "Retrieve a page of books")
    @Parameter(name = "pageNumber", required = true, description = "Page number to get")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books")
    @GetMapping("/{pageNumber}")
    public ResponseEntity<PagedModel<BookResponseV1>> getAll(@PositiveOrZero @PathVariable int pageNumber) {
        Page<BookResponseV1> entities = bookFacade.getAll(pageNumber);
        return ResponseEntity.ok(new PagedModel<>(entities));
    }

}
