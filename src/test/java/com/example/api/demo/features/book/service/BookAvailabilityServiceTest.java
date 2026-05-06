package com.example.api.demo.features.book.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.api.demo.feature.book.Book;
import com.example.api.demo.feature.book.bookAvailability.BookAvailability;
import com.example.api.demo.feature.book.bookAvailability.BookAvailabilityRepository;
import com.example.api.demo.feature.book.bookAvailability.BookAvailabilityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookAvailabilityService Unit Tests")
class BookAvailabilityServiceTest {

    private BookAvailabilityService bookAvailabilityService;

    @Mock
    private BookAvailabilityRepository bookAvailabilityRepository;

    @BeforeEach
    void setUp() {
        bookAvailabilityService = new BookAvailabilityService(bookAvailabilityRepository);
    }

    @Test
    @DisplayName("Should get availability by book ID")
    void testGetAvailability() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book("Test Book", "ISBN123", 2024);
        book.setId(bookId);
        BookAvailability availability = new BookAvailability(book, true);

        when(bookAvailabilityRepository.findByBookId(bookId)).thenReturn(availability);

        // Act
        BookAvailability result = bookAvailabilityService.getAvailability(bookId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isAvailable());
        verify(bookAvailabilityRepository, times(1)).findByBookId(bookId);
    }

    @Test
    @DisplayName("Should update availability")
    void testUpdateAvailability() {
        // Arrange
        Book book = new Book("Test Book", "ISBN123", 2024);
        book.setId(1L);
        BookAvailability availability = new BookAvailability(book, true);

        when(bookAvailabilityRepository.save(availability)).thenReturn(availability);

        // Act
        BookAvailability result = bookAvailabilityService.updateAvailability(availability);

        // Assert
        assertNotNull(result);
        verify(bookAvailabilityRepository, times(1)).save(availability);
    }

    @Test
    @DisplayName("Should update availability status for a book")
    void testUpdateAvailabilityStatus() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book("Test Book", "ISBN123", 2024);
        book.setId(bookId);
        BookAvailability availability = new BookAvailability(book, true);

        when(bookAvailabilityRepository.findByBookId(bookId)).thenReturn(availability);
        when(bookAvailabilityRepository.save(availability)).thenReturn(availability);

        // Act
        BookAvailability result = bookAvailabilityService.updateAvailabilityStatus(bookId, false);

        // Assert
        assertNotNull(result);
        assertFalse(result.isAvailable());
        verify(bookAvailabilityRepository, times(1)).findByBookId(bookId);
        verify(bookAvailabilityRepository, times(1)).save(availability);
    }

    @Test
    @DisplayName("Should delete availability by book ID")
    void testDeleteAvailability() {
        // Arrange
        Long bookId = 1L;
        Book book = new Book("Test Book", "ISBN123", 2024);
        book.setId(bookId);
        BookAvailability availability = new BookAvailability(book, true);

        when(bookAvailabilityRepository.findByBookId(bookId)).thenReturn(availability);

        // Act
        bookAvailabilityService.deleteAvailability(bookId);

        // Assert
        verify(bookAvailabilityRepository, times(1)).findByBookId(bookId);
        verify(bookAvailabilityRepository, times(1)).delete(availability);
    }

    @Test
    @DisplayName("Should handle null availability gracefully on delete")
    void testDeleteNullAvailability() {
        // Arrange
        Long bookId = 99L;

        when(bookAvailabilityRepository.findByBookId(bookId)).thenReturn(null);

        // Act & Assert - should not throw
        assertDoesNotThrow(() -> bookAvailabilityService.deleteAvailability(bookId));
        verify(bookAvailabilityRepository, never()).delete(any());
    }
}
