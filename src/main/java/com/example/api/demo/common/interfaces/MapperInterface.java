package com.example.api.demo.common.interfaces;

import java.util.List;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface MapperInterface<T, ReqDto, ResDto> {
    T dtoToEntity(ReqDto dto);

    ResDto entityToDto(T entity);

    List<T> dtoListToEntityList(List<ReqDto> dtos);

    List<ResDto> entityListToDtoList(List<T> entities);
}
