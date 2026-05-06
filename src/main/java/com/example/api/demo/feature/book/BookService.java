package com.example.api.demo.feature.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.api.demo.common.exception.BookNotFoundException;
import com.example.api.demo.feature.author.Author;
import com.example.api.demo.feature.author.AuthorRepository;
import com.example.api.demo.feature.book.bookAvailability.BookAvailability;
import com.example.api.demo.feature.book.bookAvailability.BookAvailabilityService;
import com.example.api.demo.feature.book.v1.BookMapperV1;
import com.example.api.demo.feature.book.v1.BookRequestV1;
import com.example.api.demo.feature.book.v1.BookResponseV1;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookAvailabilityService bookAvailabilityService;

    public BookService(BookRepository bookRepository, BookAvailabilityService bookAvailabilityService) {
        this.bookRepository = bookRepository;
        this.bookAvailabilityService = bookAvailabilityService;
    }

    @Cacheable(value = "all")
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "byId", key = "#id")
    public Optional<Book> getById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book;
        } else {
            throw new BookNotFoundException("Book with id " + bookId + " not found.");
        }
    }

    public Book save(Book book) {
        BookAvailability bookAvailability = new BookAvailability(book, true);
        bookAvailabilityService.save(bookAvailability);
        book.setAvailability(bookAvailability);
        return bookRepository.save(book);
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        getById(id);
        bookRepository.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public Book update(Book book) {
        return bookRepository.save(book);
    }

    // One loan made at a time
    public synchronized void updateBookAvailability(Long bookId, boolean available) {
        bookAvailabilityService.updateAvailabilityStatus(bookId, available);
    }
}