package com.example.api.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.api.demo.entity.Author;

@Service
public class AuthorService extends AbstractGenericService<Author, Long> {

    public AuthorService(JpaRepository<Author, Long> repository) {
        super(repository);
    }

}
