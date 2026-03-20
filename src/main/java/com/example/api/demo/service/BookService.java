package com.example.api.demo.service;


import org.springframework.stereotype.Service;

import com.example.api.demo.entity.Book;
import com.example.api.demo.repository.BookRepository;

// Spring
@Service
public class BookService extends AbstractGenericService<Book, Long>{

    public BookService(BookRepository repository) {
        super(repository);
    }

}
