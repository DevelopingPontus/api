package com.example.api.demo.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
import com.example.api.demo.entity.Book;
import com.example.api.demo.repository.BookRepository;
import com.example.api.demo.service.mapper.BookMapper;

@Service
public class BookService {
    private final BookRepository repository;
    private final BookMapper mapper;

    public BookService(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<BookResponse> getAll() {
        List<Book> steps = repository.findAll();
        return steps.stream().map(mapper::toDto).toList();
    }

    public BookResponse get(Long id) {
        Book step = repository.findById(id).orElseThrow(() -> new RuntimeException("Step not found"));
        return mapper.toDto(step);
    }

    public void create(BookRequest request) {
        Book entity = mapper.toEntity(request);
        repository.save(entity);
    }

}


