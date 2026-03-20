package com.example.api.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.entity.Author;
import com.example.api.demo.repository.AuthorRepository;

@Service
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Author create(Author author) {
        return repository.save(author);
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Author findById(Long id) {
        return repository.findById(id).orElse(null);

    }

    public List<Author> getAll() {
        return repository.findAll();

    }

    public Author update(Long id, Author author) {
        if (repository.existsById(id)) {
            author.setId(id);
            return repository.save(author);
        }
        return null;
    }

}
