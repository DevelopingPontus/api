package com.example.api.demo.feature.author.v1;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.common.wrapper.GenericWrapperResponse;
import com.example.api.demo.feature.author.AuthorFacade;

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
public class AuthorControllerV1 {

    protected final AuthorFacade authorFacade;
    protected final String version;

    protected AuthorControllerV1(AuthorFacade authorFacade) {
        this.authorFacade = authorFacade;
        this.version = "v1";
    }

    @Operation(summary = "Get all entities", description = "Retrieve a list of all entities")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of entities")
    @GetMapping

    public ResponseEntity<GenericWrapperResponse<AuthorResponeV1>> getAll() {
        List<AuthorResponeV1> entities = authorFacade.getAll();
        GenericWrapperResponse<AuthorResponeV1> wrapperResponse = new GenericWrapperResponse<>(entities, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Get entity by ID", description = "Retrieve an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the entity")
    @ApiResponse(responseCode = "404", description = "Entity not found")
    @GetMapping("{id}")

    public ResponseEntity<GenericWrapperResponse<AuthorResponeV1>> getById(@PathVariable Long id) {
        AuthorResponeV1 entity = authorFacade.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        List<AuthorResponeV1> singleList = List.of(entity);
        GenericWrapperResponse<AuthorResponeV1> wrapperResponse = new GenericWrapperResponse<>(singleList, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Save a new entity", description = "Create a new entity")
    @ApiResponse(responseCode = "201", description = "Entity created successfully")
    @PostMapping

    public ResponseEntity<GenericWrapperResponse<AuthorResponeV1>> save(@RequestBody @Validated AuthorRequestV1 authorRequests) {
        List<AuthorResponeV1> savedAuthor = List.of(authorFacade.save(authorRequests));
        GenericWrapperResponse<AuthorResponeV1> wrapperResponse = new GenericWrapperResponse<>(savedAuthor, version);
        return ResponseEntity.status(201).body(wrapperResponse);
    }

    @Operation(summary = "Delete an entity by ID", description = "Delete an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to delete")
    @ApiResponse(responseCode = "204", description = "Entity deleted successfully")
    @DeleteMapping("{id}")

    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        authorFacade.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an entity by ID", description = "Update an entity by its ID. Only implemented for Loan entities.")
    @Parameter(name = "id", required = true, description = "ID of the entity to update")
    @ApiResponse(responseCode = "200", description = "Entity updated successfully")
    @PutMapping("{id}")

    public ResponseEntity<GenericWrapperResponse<AuthorResponeV1>> update(@PathVariable Long id, @RequestBody @Validated AuthorRequestV1 authorRequest) {
        List<AuthorResponeV1> updatedAuthor = List.of(authorFacade.update(id, authorRequest));
        GenericWrapperResponse<AuthorResponeV1> wrapperResponse = new GenericWrapperResponse<>(updatedAuthor, version);
        return ResponseEntity.ok(wrapperResponse);
    }


}
