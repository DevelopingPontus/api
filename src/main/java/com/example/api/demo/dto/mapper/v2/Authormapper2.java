package com.example.api.demo.dto.mapper.v2;

import org.springframework.stereotype.Component;

import com.example.api.demo.dto.v2.AuthorRequest2;
import com.example.api.demo.dto.v2.AuthorResponse2;
import com.example.api.demo.entity.Author;

@Component
public class Authormapper2 {
    // Mappers
    public Author toEntity(AuthorRequest2 req) {
        return new Author(req.firstName(), req.lastName());
    }

    public AuthorResponse2 toDto(Author ent) {
        return new AuthorResponse2(
                ent.getId(),
                ent.getFirstName(),
                ent.getLastName());
    }
}
