package com.example.api.demo.service;


import org.springframework.stereotype.Service;

import com.example.api.demo.repository.BookStatusRepository;
import com.example.api.demo.repository.BookRepository;
import com.example.api.demo.entity.Book;

// Spring
@Service
public class BookServiceV2 {
    private final BookRepository bookRepository;
    private final BookStatusRepository statusRepository;

    public BookServiceV2(BookRepository repository, BookStatusRepository statusRepository) {
        this.bookRepository = repository;
        this.statusRepository = statusRepository;
    }

    // public List<BookStatus> findAll() {
    //     return statusRepository.findAll();
    // }

    // public Set<Object> findById(Long id) throws BookNotFoundException {
    // }


    public Book save(Book req) {
        return bookRepository.save(req);
        // return statusRepository.save(new BookStatus(book.getId()));
    }
}
