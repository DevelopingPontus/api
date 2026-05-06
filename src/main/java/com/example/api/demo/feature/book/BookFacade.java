package com.example.api.demo.feature.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.example.api.demo.common.exception.BookNotFoundException;
import com.example.api.demo.feature.author.Author;
import com.example.api.demo.feature.author.AuthorRepository;
import com.example.api.demo.feature.author.AuthorService;
import com.example.api.demo.feature.book.bookAvailability.BookAvailability;
import com.example.api.demo.feature.book.bookAvailability.BookAvailabilityService;
import com.example.api.demo.feature.book.v1.BookMapperV1;
import com.example.api.demo.feature.book.v1.BookRequestV1;
import com.example.api.demo.feature.book.v1.BookResponseV1;

public class BookFacade {
    
    private final BookService bookService;
    private final AuthorService authorService;
    private final BookMapperV1 bookMapper;
    private final BookAvailabilityService bookAvailabilityService;


    public BookFacade(BookService bookService, AuthorService authorService, BookMapperV1 bookMapper,
            BookAvailabilityService bookAvailabilityService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
        this.bookAvailabilityService = bookAvailabilityService;
    }

    @Cacheable(value = "all")
    public List<BookResponseV1> getAll() {
        List<Book> books = bookService.getAll();
        return bookMapper.entityListToDtoList(books);
    }

    @Cacheable(value = "byId", key = "#id")
    public BookResponseV1 getById(Long bookId) {
        Book book = bookService.getById(bookId).get();
        return bookMapper.entityToDto(book);
    }

    public List<BookResponseV1> save(List<BookRequestV1> bookRequests) {
        List<Book> books = new ArrayList<>();
        for (BookRequestV1 bookRequest : bookRequests) {
            Optional<Author> author = authorService.getByName(bookRequest.author());
            if (author.isEmpty()) {
                Author newAuthor = new Author(bookRequest.author());
                Book newBook = bookMapper.dtoToEntity(bookRequest);
                newBook.setAuthor(newAuthor);
                newAuthor.getBooks().add(newBook);
                authorService.save(newAuthor);
                bookService.save(newBook);
                books.add(newBook);
            } else {
                Book newBook = bookMapper.dtoToEntity(bookRequest);
                newBook.setAuthor(author.get());
                author.get().getBooks().add(newBook);
                authorService.save(author.get());
                bookService.save(newBook);
                books.add(newBook);
            }
        }
        return bookMapper.entityListToDtoList(books);
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        bookService.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public BookResponseV1 update(Long id, BookRequestV1 bookRequest) {
        Optional<Book> book = bookService.getById(id);
        if (book.isPresent()) {
            if (bookRequest.title() != book.get().getTitle()) {
                book.get().setTitle(bookRequest.title());
            }
            if (bookRequest.author() != book.get().getAuthor().getName()) {
                Author author = authorService.getByName(bookRequest.author()).get();
                book.get().setAuthor(author);
            }
            if (bookRequest.isbn() != book.get().getIsbn()) {
                book.get().setIsbn(bookRequest.isbn());
            }
            if (bookRequest.publishedYear() != book.get().getPublishedYear()) {
                book.get().setPublishedYear(bookRequest.publishedYear());
            }
        }
        Book updatedBook = bookService.save(book.get());
        return bookMapper.entityToDto(updatedBook);
    }

    public synchronized void updateBookAvailability(Long bookId, boolean available) {
        bookAvailabilityService.updateAvailabilityStatus(bookId, available);
    }
}
