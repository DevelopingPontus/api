package com.example.api.demo.generic.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.api.demo.generic.interfaces.EntityInterface;
import com.example.api.demo.generic.interfaces.GenericRepository;
import com.example.api.demo.generic.interfaces.MapperInterface;

import jakarta.persistence.MappedSuperclass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MappedSuperclass
public abstract class GenericService<T extends EntityInterface, ReqDto, ResDto> {

    protected final GenericRepository<T> repository;
    protected final MapperInterface<T, ReqDto, ResDto> mapper;

    @Autowired
    protected GenericService(GenericRepository<T> repository, MapperInterface<T, ReqDto, ResDto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ResDto> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    public ResDto getById(Long id) {
        return mapper.entityToDto(repository.findById(id).orElse(null));
    }

    public List<ResDto> save(List<ReqDto> dtos) {
        List<T> entities = mapper.dtoListToEntityList(dtos);
        repository.saveAll(entities);
        return mapper.entityListToDtoList(entities);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<ResDto> update(Long id) {
        List<T> entities = List.of(repository.findById(id).orElseThrow());
        return mapper.entityListToDtoList(entities);
    }
}
