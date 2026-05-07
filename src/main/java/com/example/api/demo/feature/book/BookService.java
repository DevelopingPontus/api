package com.example.api.demo.feature.book;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.common.exception.BookNotFoundException;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        if (bookRepository.findAll().isEmpty()) {
            throw new BookNotFoundException("No books were found");
        }
        return bookRepository.findAll();
    }

    public Book getById(Long bookId) {
        if (bookRepository.findById(bookId).isPresent()) {
            return bookRepository.findById(bookId).get();
        } else {
            throw new BookNotFoundException("Book with id " + bookId + " not found.");
        }
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        getById(id);
        bookRepository.deleteById(id);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }
}