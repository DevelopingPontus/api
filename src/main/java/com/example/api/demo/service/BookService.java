package com.example.api.demo.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
import com.example.api.demo.entity.Book;
import com.example.api.demo.exception.ErrorSavingException;
import com.example.api.demo.repository.BookRepository;
import com.example.api.demo.service.mapper.BookMapper;

// Spring
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

    @Transactional
    public BookResponse get(Long id) throws NotFoundException {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    public void create(BookRequest request){
        Book entity = mapper.toEntity(request);
        repository.save(entity);
    }

}
