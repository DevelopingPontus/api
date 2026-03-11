package com.example.api.demo.service.mapper;

import org.springframework.stereotype.Component;

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
import com.example.api.demo.entity.Book;

@Component
public class BookMapper {
    public BookResponse toDto(Book entity) {
        return new BookResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIsbn(),
                entity.getYear());
    }

    public Book toEntity(BookRequest request) {
        return new Book(
                request.id(),
                request.title(),
                request.description(),
                request.isbn(),
                request.year());
    }
}
