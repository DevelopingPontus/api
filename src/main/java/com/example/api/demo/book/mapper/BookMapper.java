package com.example.api.demo.book.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.api.demo.author.Author;
import com.example.api.demo.book.Book;
import com.example.api.demo.book.dto.v1.BookReq1;
import com.example.api.demo.book.dto.v1.BookRes1;
import com.example.api.demo.generic.interfaces.MapperInterface;
import java.util.Collections;

@Component
public class BookMapper implements MapperInterface<Book, BookReq1, BookRes1> {
    @Override
    public BookRes1 entityToDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookRes1(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getIsbn(), book.getPublishedYear(), book.isAvailable());
    }

    @Override
    public Book dtoToEntity(BookReq1 bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        return new Book(bookDTO.title(), bookDTO.isbn(), bookDTO.publishedYear());
    }

    @Override
    public List<Book> dtoListToEntityList(List<BookReq1> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<BookRes1> entityListToDtoList(List<Book> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }
}
