package com.example.api.demo.feature.book.v2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.api.demo.feature.book.Book;
import com.example.api.demo.feature.book.v1.BookMapperV1;
import com.example.api.demo.feature.book.v1.BookResponseV1;

@Component
public class BookFacadePageable {

    private final BookServicePageable bookService;
    private final BookMapperV1 bookMapper;

    public BookFacadePageable(BookServicePageable bookService, BookMapperV1 bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    public Page<BookResponseV1> getAll(int page) {
        Page<Book> books = bookService.getAll(Pageable.ofSize(2).withPage(page));
        return bookMapper.pageEntityToDtoPage(books);
    }

}
