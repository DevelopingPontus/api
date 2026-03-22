package com.example.api.demo.generic.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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

    public ResponseEntity<List<DTO>> getAll() {
        List<DTO> entities = mapper.entityListToDtoList(service.getAll());
        return ResponseEntity.ok(entities);
    }

    public ResponseEntity<DTO> getById(Long id) {
        DTO entity = mapper.entityToDto(service.getById(id));
        if (entity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entity);
    }

    public ResponseEntity<DTO> save(T entity) {
        DTO savedEntity = mapper.entityToDto(service.save(entity));
        return ResponseEntity.status(201).body(savedEntity);
    }

    public ResponseEntity<Void> deleteById(Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
