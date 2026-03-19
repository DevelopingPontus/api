package com.example.api.demo.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.api.demo.entity.Book;

// Spring
@Service
public class BookService extends AbstractGenericService<Book, Long>{

    public BookService(JpaRepository<Book, Long> repository) {
        super(repository);
    }

}
