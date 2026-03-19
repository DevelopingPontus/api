package com.example.api.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityNotFoundException;

public abstract class AbstractGenericService<T, ID> implements GenericService<T, ID> {
    protected final JpaRepository<T, ID> repository;

    protected AbstractGenericService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }


    // @Override
    // public T save(T entity) {
    //     return entity;
    // }
    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T update(ID id, T updateEntity) {
        if (findById(id).isEmpty()) {
            throw new EntityNotFoundException("Entity not found for id: " + id);
        }
        return repository.save(updateEntity);
    }

    @Override
    public void delete(ID id) {
        if (findById(id).isEmpty()) {
            throw new EntityNotFoundException("Entity to delete not found for id: " + id);
        } else {
            repository.deleteById(id);
        }
    }

}
