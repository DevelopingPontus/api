package com.example.api.demo.features.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.api.demo.features.author.Author;
import com.example.api.demo.features.author.AuthorRepository;
import com.example.api.demo.features.book.bookAvailability.BookAvailability;
import com.example.api.demo.features.book.bookAvailability.BookAvailabilityService;

@Service
public class BookService {
    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final BookMapper mapper;
    private final BookAvailabilityService bookAvailabilityService;

    public BookService(BookRepository repository, BookMapper mapper, AuthorRepository authorRepository,
            BookAvailabilityService bookAvailabilityService) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.mapper = mapper;
        this.bookAvailabilityService = bookAvailabilityService;
    }

    @Cacheable(value = "all")
    public List<BookRes1> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public BookRes1 getById(Long id) {
        return mapper.entityToDto(repository.findById(id).orElse(null));
    }

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

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = { "all", "byId" }, allEntries = true)
    public List<BookRes1> update(Long id) {
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