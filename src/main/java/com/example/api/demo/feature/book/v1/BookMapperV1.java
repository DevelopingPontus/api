package com.example.api.demo.feature.book.v1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.example.api.demo.feature.book.Book;

import java.util.Collections;

@Component
public class BookMapperV1 {
    public BookResponseV1 entityToDto(Book book) {
        if (book == null) {
            return null;
        }
        return new BookResponseV1(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getIsbn(),
                book.getPublishedYear(), book.isAvailable());
    }

    public Book dtoToEntity(BookRequestV1 bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        return new Book(bookDTO.title(), bookDTO.isbn(), bookDTO.publishedYear());
    }

    public List<Book> dtoListToEntityList(List<BookRequestV1> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::dtoToEntity).toList();
    }

    public List<BookResponseV1> entityListToDtoList(List<Book> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream().map(this::entityToDto).toList();
    }


    /**
     * Converts a Page of Entities to a Page of DTOs.
     */
    public Page<BookResponseV1> pageEntityToDtoPage(Page<Book> bookPage) {
        
        // Map the content list
        List<BookResponseV1> dtoList = entityListToDtoList(bookPage.getContent());
        
        // Return a new PageImpl using the mapped list and original pagination metadata
        return new PageImpl<>(dtoList, bookPage.getPageable(), bookPage.getTotalElements());
    }
}
