package com.example.api.demo.feature.author.v1;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.api.demo.feature.author.Author;
import com.example.api.demo.feature.book.v1.BookMapperV1;

@Component
public class AuthorMapperV1 {
    private final BookMapperV1 bookMapper;

    public AuthorMapperV1(BookMapperV1 bookMapper) {
        this.bookMapper = bookMapper;
    }

    public AuthorResponeV1 entityToDto(Author entity) {
        if (entity == null) {
            return null;
        }
        return new AuthorResponeV1(entity.getId(), entity.getName(), bookMapper.entityListToDtoList(entity.getBooks()));
    }

    public Author dtoToEntity(AuthorRequestV1 dto) {
        if (dto == null) {
            return null;
        }
        return new Author(dto.name());
    }

    public List<Author> dtoListToEntityList(List<AuthorRequestV1> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    public List<AuthorResponeV1> entityListToDtoList(List<Author> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }
}
