package com.example.api.demo.feature.book;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.demo.feature.author.Author;
import com.example.api.demo.feature.author.AuthorService;
import com.example.api.demo.feature.book.v1.BookMapperV1;
import com.example.api.demo.feature.book.v1.BookRequestV1;
import com.example.api.demo.feature.book.v1.BookResponseV1;

@Component
public class BookFacade {

    private final BookService bookService;
    private final AuthorService authorService;
    private final BookMapperV1 bookMapper;

    public BookFacade(BookService bookService, AuthorService authorService, BookMapperV1 bookMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
    }

    public List<BookResponseV1> getAll() {
        List<Book> books = bookService.getAll();
        return bookMapper.entityListToDtoList(books);
    }

    public BookResponseV1 getById(Long bookId) {
        Book book = bookService.getById(bookId);
        return bookMapper.entityToDto(book);
    }

    @Transactional
    public BookResponseV1 save(BookRequestV1 bookRequest) {
        Book newBook;
        Author author = authorService.getByName(bookRequest.author());
        if (author == null) {
            Author newAuthor = new Author(bookRequest.author());
            newBook = bookMapper.dtoToEntity(bookRequest);
            newBook.setAuthor(newAuthor);
            List<Book> books = newAuthor.getBooks();
            books.add(newBook);
            newAuthor.setBooks(books);
            authorService.save(newAuthor);
            bookService.save(newBook);
        } else {
            newBook = bookMapper.dtoToEntity(bookRequest);
            newBook.setAuthor(author);
            List<Book> books = author.getBooks();
            books.add(newBook);
            author.setBooks(books);
            authorService.save(author);
            bookService.save(newBook);
        }
        return bookMapper.entityToDto(newBook);
    }

    @Transactional
    public void deleteById(Long id) {
        bookService.deleteById(id);
    }

    @Transactional
    public BookResponseV1 update(Long id, BookRequestV1 bookRequest) {
        Book book = bookService.getById(id);
        book.setTitle(bookRequest.title());
        Author author = authorService.getByName(bookRequest.author());
        book.setAuthor(author);
        book.setIsbn(bookRequest.isbn());
        book.setPublishedYear(bookRequest.publishedYear());
        Book updatedBook = bookService.save(book);
        return bookMapper.entityToDto(updatedBook);
    }
}
