package com.example.api.demo.feature.book;

import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.api.demo.common.exception.BookNotFoundException;

@Service
public class BookService {
    
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "book")
    public List<Book> getAll() {
        if (bookRepository.findAll().isEmpty()) {
            throw new BookNotFoundException("No books were found");
        }
        return bookRepository.findAll();
    }

    @Cacheable(value = "book", key = "#bookId")
    public Book getById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("Book with id " + bookId + " not found.");
        }
    }

    @CacheEvict(value = "book", allEntries = true)
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(value = "book", key = "#bookId")
    public void deleteById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            bookRepository.deleteById(bookId);
        } else {
            throw new BookNotFoundException("Book with id " + bookId + " was not found.");
        }
    }

    @CacheEvict(value = "book", key = "#book.getId()")
    public Book update(Book book) {
        return bookRepository.save(book);
    }
}