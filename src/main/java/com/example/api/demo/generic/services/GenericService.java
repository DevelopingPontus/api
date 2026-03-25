package com.example.api.demo.generic.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.api.demo.generic.interfaces.EntityInterface;
import com.example.api.demo.generic.interfaces.GenericRepository;

import jakarta.persistence.MappedSuperclass;

import java.util.List;

@MappedSuperclass
public abstract class GenericService<T extends EntityInterface> {

    protected final GenericRepository<T> repository;

    @Autowired
    protected GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    public T getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public T save(T entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
