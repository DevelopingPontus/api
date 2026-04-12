package com.example.api.demo.features.author.repository;

import com.example.api.demo.common.interfaces.GenericRepository;
import com.example.api.demo.features.author.entity.Author;

public interface AuthorRepository extends GenericRepository<Author> {

    public Author findByName(String name);

}
