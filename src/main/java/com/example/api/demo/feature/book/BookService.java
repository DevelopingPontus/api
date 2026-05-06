package com.example.api.demo.feature.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.api.demo.feature.author.Author;
import com.example.api.demo.feature.author.AuthorRepository;
import com.example.api.demo.feature.book.bookAvailability.BookAvailability;
import com.example.api.demo.feature.book.bookAvailability.BookAvailabilityService;
import com.example.api.demo.feature.book.v1.BookMapperV1;
import com.example.api.demo.feature.book.v1.BookRequestV1;
import com.example.api.demo.feature.book.v1.BookResponseV1;

@Service
public class BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final BookMapperV1 mapper;
    private final BookAvailabilityService bookAvailabilityService;

    public BookService(BookRepository repository, BookMapperV1 mapper, AuthorRepository authorRepository,
            BookAvailabilityService bookAvailabilityService) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.mapper = mapper;
        this.bookAvailabilityService = bookAvailabilityService;
    }

    @Cacheable(value = "all")
    public List<BookResponseV1> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public BookResponseV1 getById(Long id) {
        return mapper.entityToDto(repository.findById(id).orElse(null));
    }

    public List<BookResponseV1> save(List<BookRequestV1> dtos) {
        List<Book> books = new ArrayList<>();
        for (BookRequestV1 bookReq1 : dtos) {
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

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public List<BookResponseV1> update(Long id) {
        Book entity = repository.findById(id).orElseThrow();
        // Merge/update entity with dto data (you'll need to implement this logic)
        Book updated = repository.save(entity);
        return List.of(mapper.entityToDto(updated));
    }

    // One loan made at a time
    public synchronized void updateBookAvailability(Long bookId, boolean available) {
        bookAvailabilityService.updateAvailabilityStatus(bookId, available);
    }
}