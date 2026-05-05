package com.example.api.demo.features.book.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api.demo.features.author.Author;
import com.example.api.demo.features.author.AuthorRepository;
import com.example.api.demo.features.book.Book;
import com.example.api.demo.features.book.BookMapper;
import com.example.api.demo.features.book.BookRepository;
import com.example.api.demo.features.book.BookReq1;
import com.example.api.demo.features.book.BookRes1;
import com.example.api.demo.features.book.BookService;
import com.example.api.demo.features.book.bookAvailability.BookAvailability;
import com.example.api.demo.features.book.bookAvailability.BookAvailabilityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService Unit Tests")
class BookServiceTest {

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookAvailabilityService bookAvailabilityService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository, bookMapper, authorRepository, bookAvailabilityService);
    }

    @Test
    @DisplayName("Should update book availability")
    void testUpdateBookAvailability() {
        // Arrange
        Long bookId = 1L;
        boolean available = false;

        // Act
        bookService.updateBookAvailability(bookId, available);

        // Assert
        verify(bookAvailabilityService, times(1)).updateAvailabilityStatus(bookId, available);
    }

    @Test
    @DisplayName("Should save books with availability")
    void testSaveBooks() {
        // Arrange
        Author author = new Author("Test Author");
        BookReq1 bookReq = new BookReq1("Test Book", "Test Author", "ISBN123", 2024, true);
        Book book = new Book("Test Book", "ISBN123", 2024);
        book.setId(1L);
        book.setAuthor(author);

        when(authorRepository.findByName("Test Author")).thenReturn(null);
        when(bookMapper.dtoToEntity(bookReq)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        when(bookAvailabilityService.updateAvailability(any(BookAvailability.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(bookMapper.entityListToDtoList(anyList()))
                .thenReturn(List.of(new BookRes1(1L, "Test Book", "Test Author", "ISBN123", 2024, true)));

        // Act
        List<BookRes1> result = bookService.save(List.of(bookReq));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookAvailabilityService, times(1)).updateAvailability(any(BookAvailability.class));
    }

    @Test
    @DisplayName("Should reuse existing author when saving book")
    void testSaveBookWithExistingAuthor() {
        // Arrange
        Author existingAuthor = new Author("Existing Author");
        BookReq1 bookReq = new BookReq1("Test Book", "Existing Author", "ISBN123", 2024, true);
        Book book = new Book("Test Book", "ISBN123", 2024);
        book.setId(1L);
        book.setAuthor(existingAuthor);

        when(authorRepository.findByName("Existing Author")).thenReturn(existingAuthor);
        when(bookMapper.dtoToEntity(bookReq)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookAvailabilityService.updateAvailability(any(BookAvailability.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(bookMapper.entityListToDtoList(anyList()))
                .thenReturn(List.of(new BookRes1(1L, "Test Book", "Existing Author", "ISBN123", 2024, true)));

        // Act
        List<BookRes1> result = bookService.save(List.of(bookReq));

        // Assert
        assertNotNull(result);
        verify(authorRepository, never()).save(any(Author.class));
        verify(bookRepository, times(1)).save(any(Book.class));
    }
}
