package com.example.api.demo.book.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.api.demo.book.dto.v1.BookDTO;
import com.example.api.demo.book.entity.Book;
import com.example.api.demo.generic.interfaces.MapperInterface;
import java.util.Collections;

@Component
public class BookMapper implements MapperInterface<Book, BookDTO> {
    @Override
    public BookDTO entityToDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedYear());
    }

    @Override
    public Book dtoToEntity(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        return new Book(bookDTO.title(), bookDTO.author(), bookDTO.isbn(), bookDTO.publishedYear());
    }

    @Override
    public List<Book> dtoListToEntityList(List<BookDTO> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<BookDTO> entityListToDtoList(List<Book> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }
}
