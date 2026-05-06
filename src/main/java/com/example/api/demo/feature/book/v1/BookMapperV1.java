package com.example.api.demo.feature.book.v1;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.api.demo.common.interfaces.MapperInterface;
import com.example.api.demo.feature.book.Book;

import java.util.Collections;

@Component
public class BookMapperV1 implements MapperInterface<Book, BookRequestV1, BookResponseV1> {
    @Override
    public BookResponseV1 entityToDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponseV1(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getIsbn(),
                book.getPublishedYear(), book.isAvailable());
    }

    @Override
    public Book dtoToEntity(BookRequestV1 bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        return new Book(bookDTO.title(), bookDTO.isbn(), bookDTO.publishedYear());
    }

    @Override
    public List<Book> dtoListToEntityList(List<BookRequestV1> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    @Override
    public List<BookResponseV1> entityListToDtoList(List<Book> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }
}
