package com.example.api.demo.generic.interfaces;

import java.util.List;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface MapperInterface<T, DTO> {
    T dtoToEntity(DTO dto);

    DTO entityToDto(T entity);
    
    List<T> dtoListToEntityList(List<DTO> dtos);

    List<DTO> entityListToDtoList(List<T> entities);
}
