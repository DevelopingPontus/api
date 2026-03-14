package com.example.api.demo.v2.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.api.demo.repository.BookStatusRepository;
import com.example.api.demo.repository.BookRepository;
import com.example.api.demo.entity.Book;
import com.example.api.demo.entity.BookStatus;
import com.example.api.demo.exception.BookNotFoundException;

// Spring
@Service
public class BookServiceV2 {
    private final BookRepository bookRepository;
    private final BookStatusRepository statusRepository;

    public BookServiceV2(BookRepository repository, BookStatusRepository statusRepository) {
        this.bookRepository = repository;
        this.statusRepository = statusRepository;
    }

    public List<BookStatus> findAll() {
        return statusRepository.findAll();
    }

    public Set<Object> findById(Long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID ", id));
        BookStatus status = statusRepository.findById(id)
                .orElse(createStatus(id));
        return Set.of(book, status);
    }

    public Set<Object> save(Book req) {
        Book book = bookRepository.save(req);
        BookStatus status = createStatus(book.getId());
        return Set.of(book, status);
    }

    public BookStatus createStatus(Long id) {
        return statusRepository.save(new BookStatus(id, true));
    }
}
