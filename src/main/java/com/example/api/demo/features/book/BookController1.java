package com.example.api.demo.features.book;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.common.wrappers.GenericWrapperResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController1 {

    protected final BookService service;
    protected final String version;

    protected BookController1(BookService service) {
        this.service = service;
        this.version = "v1";
    }

    @Operation(summary = "Get all entities", description = "Retrieve a list of all entities")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of entities")
    @GetMapping

    public ResponseEntity<GenericWrapperResponse<BookRes1>> getAll() {
        List<BookRes1> entities = service.getAll();
        GenericWrapperResponse<BookRes1> wrapperResponse = new GenericWrapperResponse<>(entities, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Get entity by ID", description = "Retrieve an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the entity")
    @ApiResponse(responseCode = "404", description = "Entity not found")
    @GetMapping("{id}")

    public ResponseEntity<GenericWrapperResponse<BookRes1>> getById(@PathVariable Long id) {
        BookRes1 entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        List<BookRes1> singleList = List.of(entity);
        GenericWrapperResponse<BookRes1> wrapperResponse = new GenericWrapperResponse<>(singleList, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Save a new entity", description = "Create a new entity")
    @ApiResponse(responseCode = "201", description = "Entity created successfully")
    @PostMapping

    public ResponseEntity<GenericWrapperResponse<BookRes1>> save(@RequestBody @Validated List<BookReq1> dtos) {
        List<BookRes1> savedDto = service.save(dtos);
        GenericWrapperResponse<BookRes1> wrapperResponse = new GenericWrapperResponse<>(savedDto, version);
        return ResponseEntity.status(201).body(wrapperResponse);
    }

    @Operation(summary = "Delete an entity by ID", description = "Delete an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to delete")
    @ApiResponse(responseCode = "204", description = "Entity deleted successfully")
    @DeleteMapping("{id}")

    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an entity by ID", description = "Update an entity by its ID. Only implemented for Loan entities.")
    @Parameter(name = "id", required = true, description = "ID of the entity to update")
    @ApiResponse(responseCode = "200", description = "Entity updated successfully")
    @PutMapping("{id}")

    public ResponseEntity<GenericWrapperResponse<BookRes1>> update(@PathVariable Long id) {
        List<BookRes1> updatedDtos = service.update(id);
        GenericWrapperResponse<BookRes1> wrapperResponse = new GenericWrapperResponse<>(updatedDtos, version);
        return ResponseEntity.ok(wrapperResponse);
    }

}
