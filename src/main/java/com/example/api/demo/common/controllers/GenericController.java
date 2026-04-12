package com.example.api.demo.common.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.api.demo.common.interfaces.EntityInterface;
import com.example.api.demo.common.services.GenericService;
import com.example.api.demo.common.wrappers.GenericWrapperResponse;

import jakarta.persistence.MappedSuperclass;

import java.util.List;

@MappedSuperclass
public abstract class GenericController<T extends EntityInterface, ReqDto, ResDto> {

    protected final GenericService<T, ReqDto, ResDto> service;
    protected final String version;

    @Autowired
    protected GenericController(GenericService<T, ReqDto, ResDto> service, String version) {
        this.service = service;
        this.version = version;
    }

    @Operation(summary = "Get all entities", description = "Retrieve a list of all entities")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of entities")
    @GetMapping

    public ResponseEntity<GenericWrapperResponse<ResDto>> getAll() {
        List<ResDto> entities = service.getAll();
        GenericWrapperResponse<ResDto> wrapperResponse = new GenericWrapperResponse<>(entities, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Get entity by ID", description = "Retrieve an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the entity")
    @ApiResponse(responseCode = "404", description = "Entity not found")
    @GetMapping("{id}")

    public ResponseEntity<GenericWrapperResponse<ResDto>> getById(@PathVariable Long id) {
        ResDto entity = service.getById(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        List<ResDto> singleList = List.of(entity);
        GenericWrapperResponse<ResDto> wrapperResponse = new GenericWrapperResponse<>(singleList, version);
        return ResponseEntity.ok(wrapperResponse);
    }

    @Operation(summary = "Save a new entity", description = "Create a new entity")
    @ApiResponse(responseCode = "201", description = "Entity created successfully")
    @PostMapping

    public ResponseEntity<GenericWrapperResponse<ResDto>> save(@RequestBody @Validated List<ReqDto> dtos) {
        List<ResDto> savedDto = service.save(dtos);
        GenericWrapperResponse<ResDto> wrapperResponse = new GenericWrapperResponse<>(savedDto, version);
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

    @Operation(summary = "Only works on Loan")
    @Parameter(name = "id", required = true, description = "ID of the entity to update")
    @ApiResponse(responseCode = "204", description = "Entity updated successfully")
    @PutMapping("{id}")

    public ResponseEntity<GenericWrapperResponse<ResDto>> update(@PathVariable Long id) {
        List<ResDto> updatedDtos = service.update(id);
        GenericWrapperResponse<ResDto> wrapperResponse = new GenericWrapperResponse<>(updatedDtos, version);
        return ResponseEntity.status(204).body(wrapperResponse);
    }

}
