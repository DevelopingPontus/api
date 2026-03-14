package com.example.api.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.entity.Book;
import com.example.api.demo.exception.BookNotFoundException;
import com.example.api.demo.repository.BookRepository;

// Spring
@Service
public class BookServiceV1 {
    private final BookRepository repository;

    public BookServiceV1(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findById(Long id) throws BookNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID ", id));
    }

    public Book save(Book req) {
        return repository.save(req);
    }

}
