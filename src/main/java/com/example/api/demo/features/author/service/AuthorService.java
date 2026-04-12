package com.example.api.demo.features.author.service;

import org.springframework.stereotype.Service;

import com.example.api.demo.features.author.dto.AuthorReq1;
import com.example.api.demo.features.author.dto.AuthorRes1;
import com.example.api.demo.features.author.entity.Author;
import com.example.api.demo.features.author.repository.AuthorRepository;
import com.example.api.demo.features.author.mapper.AuthorMapper;
import com.example.api.demo.common.services.GenericService;

@Service
public class AuthorService extends GenericService<Author, AuthorReq1, AuthorRes1> {

    protected AuthorService(AuthorRepository repository, AuthorMapper mapper) {
        super(repository, mapper);
    }

}
