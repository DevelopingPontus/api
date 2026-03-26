package com.example.api.demo.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.demo.author.Author;
import com.example.api.demo.author.AuthorRepository;
import com.example.api.demo.book.Book;
import com.example.api.demo.book.dto.v1.BookReq1;
import com.example.api.demo.book.dto.v1.BookRes1;
import com.example.api.demo.book.mapper.BookMapper;
import com.example.api.demo.book.repository.BookRepository;
import com.example.api.demo.generic.services.GenericService;

@Service
public class BookService extends GenericService<Book, BookReq1, BookRes1> {
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository repository, BookMapper mapper, AuthorRepository authorRepository) {
        super(repository, mapper);
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookRes1> save(List<BookReq1> dtos) {
        List<Book> books = new ArrayList<>();
        for (BookReq1 bookReq1 : dtos) {
            Author author;
            if (authorRepository.findByName(bookReq1.author()) == null) {
                author = new Author(bookReq1.author());
                authorRepository.save(author);
            } else {
                author = authorRepository.findByName(bookReq1.author());
            }
            Book book = mapper.dtoToEntity(bookReq1);
            book.setAuthor(author);
            repository.save(book);
            books.add(book);
        }
        return mapper.entityListToDtoList(books);
    }
}