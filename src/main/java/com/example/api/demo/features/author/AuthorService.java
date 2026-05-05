package com.example.api.demo.features.author;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    protected final AuthorRepository repository;
    protected final AuthorMapper mapper;

    protected AuthorService(AuthorRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "all")
    public List<AuthorRes1> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public AuthorRes1 getById(Long id) {
        return mapper.entityToDto(repository.findById(id).orElse(null));
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public List<AuthorRes1> save(List<AuthorReq1> dtos) {
        List<Author> entities = mapper.dtoListToEntityList(dtos);
        repository.saveAll(entities);
        return mapper.entityListToDtoList(entities);
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public List<AuthorRes1> update(Long id) {
        Author entity = repository.findById(id).orElseThrow();
        // Merge/update entity with dto data (you'll need to implement this logic)
        Author updated = repository.save(entity);
        return List.of(mapper.entityToDto(updated));
    }
}
