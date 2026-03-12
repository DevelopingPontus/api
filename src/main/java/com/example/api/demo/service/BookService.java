package com.example.api.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
import com.example.api.demo.entity.Book;
import com.example.api.demo.exception.BookNotFoundException;
import com.example.api.demo.exception.BooksNotFoundException;
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

    public List<BookResponse> getAll() throws BooksNotFoundException {
        List<Book> entities = repository.findAll();
        if (!entities.isEmpty()) {
        return entities.stream().map(mapper::toDto).toList();
        }
            throw new BooksNotFoundException("No books found");
    }

    @Transactional
    public BookResponse get(Long id) throws BookNotFoundException {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new BookNotFoundException("Book not found", id));
    }

    public void create(BookRequest request) throws ErrorSavingException{
        Book entity = mapper.toEntity(request);
        repository.save(entity);
    }

}
