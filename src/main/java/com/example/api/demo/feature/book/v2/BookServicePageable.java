package com.example.api.demo.feature.book.v2;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.api.demo.common.exception.BookNotFoundException;
import com.example.api.demo.feature.book.Book;

@Service
public class BookServicePageable {
    private final BookRepositoryPageable bookRepository;

    public BookServicePageable(BookRepositoryPageable bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "book")
    public Page<Book> getAll(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        if (page.isEmpty()) {
            throw new BookNotFoundException("No books were found");
        }
        return page;
    }

}
