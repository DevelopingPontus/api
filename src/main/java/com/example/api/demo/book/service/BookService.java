package com.example.api.demo.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.demo.book.Book;
import com.example.api.demo.book.repository.BookRepository;
import com.example.api.demo.generic.services.GenericService;

@Service
public class BookService extends GenericService<Book> {

    @Autowired
    public BookService(BookRepository repository) {
        super(repository);
    }
    
}
