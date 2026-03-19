package com.example.api.demo.controller.v2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.v2.BookRequest2;
import com.example.api.demo.dto.v2.BookResponse2;
import com.example.api.demo.entity.Book;
import com.example.api.demo.service.GenericService;

import io.swagger.v3.oas.annotations.tags.Tag;

// Spring
@RestController
@RequestMapping("api/v2/books")
// OpenApi
@Tag(name = "Books", description = "Operations related to books")
public class BookController2 extends AbstractGenericController<Book ,Long,BookRequest2, BookResponse2> {

    public BookController2(GenericService<Book, Long> service) {
        super(service, BookRequest2.class, BookResponse2.class);
    }

@Override
    protected Book convertToEntity(BookRequest2 request) {
        // You may use a mapping framework (e.g., MapStruct) here.
        return new Book(request.id(), request.title(), request.description(), request.isbn(), request.year());
    }

    @Override
    protected BookResponse2 convertToResponse(Book entity) {
        return new BookResponse2(entity.getId(), entity.getTitle(), entity.getDescription(), entity.getIsbn(), entity.getYear(), entity.isAvailable());
    }
}
