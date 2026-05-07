package com.example.api.demo.feature.author;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.example.api.demo.feature.author.v1.AuthorMapperV1;
import com.example.api.demo.feature.author.v1.AuthorRequestV1;
import com.example.api.demo.feature.author.v1.AuthorResponeV1;

@Component
public class AuthorFacade {

    protected final AuthorService authorService;
    protected final AuthorMapperV1 authorMapper;

    protected AuthorFacade(AuthorService authorService, AuthorMapperV1 authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @Cacheable(value = "all")
    public List<AuthorResponeV1> getAll() {
        return authorMapper.entityListToDtoList(authorService.getAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public AuthorResponeV1 getById(Long id) {
        return authorMapper.entityToDto(authorService.getById(id));
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public AuthorResponeV1 save(AuthorRequestV1 authorRequest) {
        Author author = authorMapper.dtoToEntity(authorRequest);
        return authorMapper.entityToDto(authorService.save(author));
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        authorService.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public AuthorResponeV1 update(Long id, AuthorRequestV1 authorRequest) {
        Author author = authorMapper.dtoToEntity(authorRequest);
        return authorMapper.entityToDto(authorService.update(id, author));
    }
}
