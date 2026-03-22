package com.example.api.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.entity.Author;
import com.example.api.demo.entity.Book;

@Service
public class BooksByAuthorService {
    private final AuthorService authorService;
    private final BookService bookService;

    public BooksByAuthorService(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    public List<Book> getBooksByAuthor(Long id) {
        Author author = authorService.findById(id);
        return author.getBooks();
    }
}
