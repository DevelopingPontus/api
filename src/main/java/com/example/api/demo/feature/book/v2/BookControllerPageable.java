package com.example.api.demo.feature.book.v2;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
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

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books")
    @GetMapping
    public PagedModel<BookResponseV1> getAll(int page) {
        Page<BookResponseV1> entities = bookFacade.getAll(page);
        return new PagedModel<>(entities);
    }

}
