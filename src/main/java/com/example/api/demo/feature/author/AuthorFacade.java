package com.example.api.demo.feature.author;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.example.api.demo.feature.author.v1.AuthorMapperV1;
import com.example.api.demo.feature.author.v1.AuthorResponeV1;

public class AuthorFacade {

    protected final AuthorService authorService;
    protected final AuthorMapperV1 mapper;

    protected AuthorFacade(AuthorService authorService, AuthorMapperV1 mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }

    @Cacheable(value = "all")
    public List<AuthorResponeV1> getAll() {
        return mapper.entityListToDtoList(authorService.getAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public AuthorResponeV1 getById(Long id) {
        return mapper.entityToDto(authorService.getById(id));
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public Author save(Author author) {
        return authorService.save(author);
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        authorService.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public List<AuthorResponeV1> update(Long id) {
        Author entity = repository.findById(id).orElseThrow();
        // Merge/update entity with dto data (you'll need to implement this logic)
        Author updated = repository.save(entity);
        return List.of(mapper.entityToDto(updated));
    }
}
