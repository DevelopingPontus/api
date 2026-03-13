package com.example.api.demo.v2.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.v2.entity.BookV2;
import com.example.api.demo.v2.repository.BookRepositoryV2;
import com.example.api.demo.exception.BookNotFoundException;

// Spring
@Service
public class BookServiceV2 {
    private final BookRepositoryV2 repository;

    public BookServiceV2(BookRepositoryV2 repository) {
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
