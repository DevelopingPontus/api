package com.example.api.demo.controller.v2;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.api.demo.service.GenericService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

public abstract class AbstractGenericController<T, ID, RQ, RS> {

    protected final GenericService<T, ID> genericService;
    private final Class<RQ> requestClass;
    private final Class<RS> responseClass;

    protected AbstractGenericController(
            GenericService<T, ID> service,
            Class<RQ> requestClass,
            Class<RS> responseClass) {
        this.genericService = service;
        this.requestClass = requestClass;
        this.responseClass = responseClass;
    }

    // Example: Create using DTO (request)
    @PostMapping
    @Operation(summary = "Create a new entity", description = "Creates a new entity based on the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RS> create(@RequestBody @Valid RQ request) {
        T entity = convertToEntity(request);
        T savedEntity = genericService.save(entity);
        return ResponseEntity.ok(convertToResponse(savedEntity));
    }

    // Get by ID
    @GetMapping("/{id}")
    // OpenApi
    @Operation(summary = "Get a book by ID", description = "Returns a single book based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book"),
            @ApiResponse(responseCode = "404", description = "Book not found with the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<RS> getById(@PathVariable ID id) {
        T entity = genericService.findById(id).orElseThrow();
        return ResponseEntity.ok(convertToResponse(entity));
    }

    // Get all entities
    @GetMapping
    // OpenApi
    @Operation(summary = "Get all books", description = "Returns all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all books"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<RS>> getAll() {
        List<T> entities = genericService.findAll();
        List<RS> responses = entities.stream()
                .map(this::convertToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    // Update using DTO (request)
    @PutMapping("/{id}")
    public ResponseEntity<RS> update(@PathVariable ID id, @RequestBody @Valid RQ request) {
        T entity = convertToEntity(request);
        T updatedEntity = genericService.update(id, entity);
        return ResponseEntity.ok(convertToResponse(updatedEntity));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        genericService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Convert from DTO to Entity (abstract method or override)
    protected abstract T convertToEntity(RQ dto);

    // Convert from Entity to Response DTO
    protected abstract RS convertToResponse(T entity);
}
