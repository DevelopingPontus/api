package com.example.api.demo.author;

import org.springframework.stereotype.Service;

import com.example.api.demo.author.dto.AuthorReq1;
import com.example.api.demo.author.dto.AuthorRes1;
import com.example.api.demo.generic.services.GenericService;

@Service
public class AuthorService extends GenericService<Author, AuthorReq1, AuthorRes1> {

    protected AuthorService(AuthorRepository repository, AuthorMapper mapper) {
        super(repository, mapper);
    }
    
}
