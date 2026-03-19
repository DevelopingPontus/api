package com.example.api.demo.service;

import java.util.List;
import java.util.Optional;


public interface GenericService<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    T update(ID id, T updateEntity);

    void delete(ID id);
}
