package com.example.api.demo.dto.mapper.v2;

import org.springframework.stereotype.Component;

import com.example.api.demo.dto.v2.BookRequest2;
import com.example.api.demo.dto.v2.BookResponse2;
import com.example.api.demo.entity.Book;

@Component
public class BookMapper2 {
    // Mappers
    public BookResponse2 toDto(Book entity) {
        return new BookResponse2(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIsbn(),
                entity.getYear(),
                entity.isAvailable());
    }

    public Book toEntity(BookRequest2 request) {
        return new Book(
                request.id(),
                request.title(),
                request.description(),
                request.isbn(),
                request.year(),
                request.available());
    }
}
