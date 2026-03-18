package com.example.api.demo.controller.v2;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.mapper.v2.Authormapper2;
import com.example.api.demo.dto.mapper.v2.BookMapper2;
import com.example.api.demo.dto.v2.AuthorRequest2;
import com.example.api.demo.dto.v2.AuthorResponse2;
import com.example.api.demo.dto.v2.BookResponse2;
import com.example.api.demo.dto.v2.ResponseWrapper;
import com.example.api.demo.entity.Author;
import com.example.api.demo.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController

@RequestMapping("api/v2/authors")
public class AuthorController2 {
    private final AuthorService service;
    private final Authormapper2 authMapper;
    private final BookMapper2 bookMapper;

    public AuthorController2(AuthorService service, Authormapper2 authMapper, BookMapper2 bookMapper) {
        this.service = service;
        this.authMapper = authMapper;
        this.bookMapper = bookMapper;
    }

    // Spring
    @PostMapping
    // OpenApi
    @Operation(summary = "Create a new author", description = "Creates a new author based on the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseWrapper<AuthorResponse2>> create(@RequestBody @Valid AuthorRequest2 req)
            throws URISyntaxException {
        Author ent = service.create(authMapper.toEntity(req));
        AuthorResponse2 res = authMapper.toDto(ent);
        return ResponseEntity.created(new URI("/api/books/" + res.id())).body(new ResponseWrapper<>(res, 2));
    }

    // Read Operations
    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID", description = "Returns the details of a specific author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseWrapper<AuthorResponse2>> get(@PathVariable Long id) {
        Author ent = service.findById(id);
        if (ent == null) {
            return ResponseEntity.notFound().build();
        }
        AuthorResponse2 res = authMapper.toDto(ent);
        return ResponseEntity.ok(new ResponseWrapper<>(res, 1));
    }

    @GetMapping
    @Operation(summary = "Get all authors", description = "Returns a list of all authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authors found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseWrapper<List<AuthorResponse2>>> getAll() {
        List<Author> ents = service.getAll();
        List<AuthorResponse2> res = ents.stream().map(this.authMapper::toDto).toList();
        return ResponseEntity.ok(new ResponseWrapper<>(res, 1));
    }

    // Update Operation
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing author", description = "Updates the details of an existing author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResponseWrapper<AuthorResponse2>> update(@PathVariable Long id,
            @RequestBody @Valid AuthorRequest2 req) {
        Author ent = service.update(id, authMapper.toEntity(req));
        if (ent == null) {
            return ResponseEntity.notFound().build();
        }
        AuthorResponse2 res = authMapper.toDto(ent);
        return ResponseEntity.ok(new ResponseWrapper<>(res, 1));
    }

    // Delete Operation
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an existing author", description = "Deletes an existing author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.delete(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // Get all books by author id
    @GetMapping("/{id}/books")
    @Operation(summary = "Get all books by author id", description = "Returns all books by author id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Author not found") })
    public ResponseEntity<List<BookResponse2>> getBooksByAuthorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllByAuthorId(id).stream().map(this.bookMapper::toDto).toList());

    }

    
}
