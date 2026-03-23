package com.example.api.demo.generic.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.example.api.demo.generic.interfaces.EntityInterface;
import com.example.api.demo.generic.interfaces.MapperInterface;
import com.example.api.demo.generic.services.GenericService;

import jakarta.persistence.MappedSuperclass;

import java.util.List;

@MappedSuperclass
public abstract class GenericController<T extends EntityInterface, DTO> {

    protected final GenericService<T> service;
    protected final MapperInterface<T, DTO> mapper;

    @Autowired
    protected GenericController(GenericService<T> service, MapperInterface<T, DTO> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Get all entities", description = "Retrieve a list of all entities")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of entities")
    @GetMapping
    public ResponseEntity<List<DTO>> getAll() {
        List<DTO> entities = mapper.entityListToDtoList(service.getAll());
        return ResponseEntity.ok(entities);
    }

    @Operation(summary = "Get entity by ID", description = "Retrieve an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to retrieve")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the entity")
    @ApiResponse(responseCode = "404", description = "Entity not found")
    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable Long id) {
        DTO entity = mapper.entityToDto(service.getById(id));
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

    @Operation(summary = "Save a new entity", description = "Create a new entity")
    @ApiResponse(responseCode = "201", description = "Entity created successfully")
    @PostMapping
    public ResponseEntity<DTO> save(@RequestBody T entity) {
        DTO savedEntity = mapper.entityToDto(service.save(entity));
        return ResponseEntity.status(201).body(savedEntity);
    }

    @Operation(summary = "Delete an entity by ID", description = "Delete an entity by its ID")
    @Parameter(name = "id", required = true, description = "ID of the entity to delete")
    @ApiResponse(responseCode = "204", description = "Entity deleted successfully")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
