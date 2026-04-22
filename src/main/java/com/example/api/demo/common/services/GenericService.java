package com.example.api.demo.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import com.example.api.demo.common.interfaces.EntityInterface;
import com.example.api.demo.common.interfaces.GenericRepository;
import com.example.api.demo.common.interfaces.MapperInterface;

import jakarta.persistence.MappedSuperclass;

import java.util.List;

@MappedSuperclass
public abstract class GenericService<T extends EntityInterface, ReqDto, ResDto> {

    protected final GenericRepository<T> repository;
    protected final MapperInterface<T, ReqDto, ResDto> mapper;

    @Autowired
    protected GenericService(GenericRepository<T> repository, MapperInterface<T, ReqDto, ResDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Cacheable(value = "all")
    public List<ResDto> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public ResDto getById(Long id) {
        return mapper.entityToDto(repository.findById(id).orElse(null));
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
        public List<ResDto> save(List<ReqDto> dtos) {
        List<T> entities = mapper.dtoListToEntityList(dtos);
        repository.saveAll(entities);
        return mapper.entityListToDtoList(entities);
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public List<ResDto> update(Long id) {
        T entity = repository.findById(id).orElseThrow();
        // Merge/update entity with dto data (you'll need to implement this logic)
        T updated = repository.save(entity);
        return List.of(mapper.entityToDto(updated));
    }
}
