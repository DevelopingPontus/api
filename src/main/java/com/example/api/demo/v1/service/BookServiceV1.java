package com.example.api.demo.v1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.v1.entity.BookV2;
import com.example.api.demo.v1.repository.BookRepositoryV1;
import com.example.api.demo.exception.BookNotFoundException;

// Spring
@Service
public class BookServiceV1 {
    private final BookRepositoryV1 repository;

    public BookServiceV1(BookRepositoryV1 repository) {
        this.repository = repository;
    }

    public List<BookV2> findAll() {
        return repository.findAll();
    }

    public BookV2 findById(Long id) throws BookNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID ", id));
    }

    public BookV2 save(BookV2 req) {
        return repository.save(req);
    }

}
