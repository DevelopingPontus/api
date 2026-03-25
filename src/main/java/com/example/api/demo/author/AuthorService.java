package com.example.api.demo.author;

import org.springframework.stereotype.Service;

import com.example.api.demo.generic.services.GenericService;

@Service
public class AuthorService extends GenericService<Author> {

    protected AuthorService(AuthorRepository repository) {
        super(repository);
    }
    
}
