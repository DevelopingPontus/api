package com.example.api.demo.features.book.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api.demo.features.author.entity.Author;
import com.example.api.demo.features.author.repository.AuthorRepository;
import com.example.api.demo.features.book.entity.Book;
import com.example.api.demo.features.book.entity.BookAvailability;
import com.example.api.demo.features.book.dto.BookReq1;
import com.example.api.demo.features.book.dto.BookRes1;
import com.example.api.demo.features.book.mapper.BookMapper;
import com.example.api.demo.features.book.repository.BookRepository;
import com.example.api.demo.common.services.GenericService;

@Service
public class BookService extends GenericService<Book, BookReq1, BookRes1> {
    private final AuthorRepository authorRepository;
    private final BookAvailabilityService bookAvailabilityService;

    @Autowired
    public BookService(BookRepository repository, BookMapper mapper, AuthorRepository authorRepository,
            BookAvailabilityService bookAvailabilityService) {
        super(repository, mapper);
        this.authorRepository = authorRepository;
        this.bookAvailabilityService = bookAvailabilityService;
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

            // Create availability record for split caching
            BookAvailability availability = new BookAvailability(book, bookReq1.available());
            bookAvailabilityService.updateAvailability(availability);
            book.setAvailability(availability);

            books.add(book);
        }
        return mapper.entityListToDtoList(books);
    }

    // One loan made at a time
    public synchronized void updateBookAvailability(Long bookId, boolean available) {
        bookAvailabilityService.updateAvailabilityStatus(bookId, available);
    }
}