package com.example.api.demo.feature.author;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.api.demo.common.exception.AuthorNotFoundException;

@Service
public class AuthorService {

    protected final AuthorRepository authorRepository;

    protected AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Cacheable(value = "author")
    public List<Author> getAll() {
        if (authorRepository.findAll().isEmpty()) {
            throw new AuthorNotFoundException("No authors were found");
        }
        return authorRepository.findAll();
    }

    @Cacheable(value = "author", key = "#id")
    public Author getById(Long id) {
        if (authorRepository.findById(id).isPresent()) {
            return authorRepository.findById(id).get();
        } else {
            throw new AuthorNotFoundException("Author with id " + id + " not found.");
        }
    }

    public Author getByName(String name) {
        if (authorRepository.findByName(name) != null){
            return authorRepository.findByName(name);
        } else {
            return null;
        }
    }

    @CacheEvict(value = "author", allEntries = true)
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @CacheEvict(value = "author", allEntries = true)
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    @CacheEvict(value = "author", key = "#authorsIdToUpdate")
    public Author update(Long authorsIdToUpdate, Author authorUpdate) {
        Author authorToUpdate = getById(authorsIdToUpdate);
        authorToUpdate.setName(authorUpdate.getName());
        return authorRepository.save(authorToUpdate);
    }
}
