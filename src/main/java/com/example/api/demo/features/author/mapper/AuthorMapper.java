package com.example.api.demo.features.author.mapper;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.api.demo.features.author.dto.AuthorReq1;
import com.example.api.demo.features.author.dto.AuthorRes1;
import com.example.api.demo.features.author.entity.Author;
import com.example.api.demo.features.book.mapper.BookMapper;
import com.example.api.demo.common.interfaces.MapperInterface;

@Component
public class AuthorMapper implements MapperInterface<Author, AuthorReq1, AuthorRes1> {
    private final BookMapper bookMapper;

    public AuthorMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public AuthorRes1 entityToDto(Author entity) {
        if (entity == null) {
            return null;
        }
        return new AuthorRes1(entity.getId(), entity.getName(), bookMapper.entityListToDtoList(entity.getBooks()));
    }

    @Override
    public Author dtoToEntity(AuthorReq1 dto) {
        if (dto == null) {
            return null;
        }
        return new Author(dto.name());
    }

    @Override
    public List<Author> dtoListToEntityList(List<AuthorReq1> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<AuthorRes1> entityListToDtoList(List<Author> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }
}
