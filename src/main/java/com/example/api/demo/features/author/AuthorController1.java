package com.example.api.demo.features.author;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.common.wrappers.GenericWrapperResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/v1/authors")
@Tag(name = "Author Controller", description = "Operations about Authors")
public class AuthorController1 {

    protected final AuthorService service;
    protected final String version;

    protected AuthorController1(AuthorService service) {
        this.service = service;
        this.version = "v1";
    }

    @Operation(summary = "Get all entities", description = "Retrieve a list of all entities")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of entities")
    @GetMapping

    public ResponseEntity<GenericWrapperResponse<AuthorRes1>> getAll() {
        List<AuthorRes1> entities = service.getAll();
        GenericWrapperResponse<AuthorRes1> wrapperResponse = new GenericWrapperResponse<>(entities, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Get entity by ID", description = "Retrieve an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the entity")
    @ApiResponse(responseCode = "404", description = "Entity not found")
    @GetMapping("{id}")

    public ResponseEntity<GenericWrapperResponse<AuthorRes1>> getById(@PathVariable Long id) {
        AuthorRes1 entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        List<AuthorRes1> singleList = List.of(entity);
        GenericWrapperResponse<AuthorRes1> wrapperResponse = new GenericWrapperResponse<>(singleList, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Save a new entity", description = "Create a new entity")
    @ApiResponse(responseCode = "201", description = "Entity created successfully")
    @PostMapping

    public ResponseEntity<GenericWrapperResponse<AuthorRes1>> save(@RequestBody @Validated List<AuthorReq1> dtos) {
        List<AuthorRes1> savedDto = service.save(dtos);
        GenericWrapperResponse<AuthorRes1> wrapperResponse = new GenericWrapperResponse<>(savedDto, version);
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

    public ResponseEntity<GenericWrapperResponse<AuthorRes1>> update(@PathVariable Long id) {
        List<AuthorRes1> updatedDtos = service.update(id);
        GenericWrapperResponse<AuthorRes1> wrapperResponse = new GenericWrapperResponse<>(updatedDtos, version);
        return ResponseEntity.ok(wrapperResponse);
    }


}
