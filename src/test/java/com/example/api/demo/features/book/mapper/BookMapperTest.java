package com.example.api.demo.features.book.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.api.demo.features.author.entity.Author;
import com.example.api.demo.features.book.dto.BookReq1;
import com.example.api.demo.features.book.dto.BookRes1;
import com.example.api.demo.features.book.entity.Book;

@DisplayName("BookMapper Unit Tests")
class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        bookMapper = new BookMapper();
    }

    @Test
    @DisplayName("Should convert entity to DTO")
    void testEntityToDto() {
        // Arrange
        Author author = new Author("Test Author");
        Book book = new Book("Test Book", "ISBN123", 2024);
        book.setId(1L);
        book.setAuthor(author);

        // Act
        BookRes1 result = bookMapper.entityToDto(book);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test Book", result.title());
        assertEquals("Test Author", result.author());
        assertEquals("ISBN123", result.isbn());
        assertEquals(2024, result.publishedYear());
        assertFalse(result.available()); // No availability entity set
    }

    @Test
    @DisplayName("Should handle null entity to DTO")
    void testEntityToDtoNull() {
        // Act
        BookRes1 result = bookMapper.entityToDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should convert DTO to entity")
    void testDtoToEntity() {
        // Arrange
        BookReq1 bookReq = new BookReq1("Test Book", "Test Author", "ISBN123", 2024, true);

        // Act
        Book result = bookMapper.dtoToEntity(bookReq);

        // Assert
        assertNotNull(result);
        assertEquals("Test Book", result.getTitle());
        assertEquals("ISBN123", result.getIsbn());
        assertEquals(2024, result.getPublishedYear());
    }

    @Test
    @DisplayName("Should handle null DTO to entity")
    void testDtoToEntityNull() {
        // Act
        Book result = bookMapper.dtoToEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Should convert entity list to DTO list")
    void testEntityListToDtoList() {
        // Arrange
        Author author = new Author("Author 1");
        Book book1 = new Book("Book 1", "ISBN001", 2024);
        book1.setId(1L);
        book1.setAuthor(author);

        Book book2 = new Book("Book 2", "ISBN002", 2023);
        book2.setId(2L);
        book2.setAuthor(author);

        // Act
        List<BookRes1> result = bookMapper.entityListToDtoList(List.of(book1, book2));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).title());
        assertEquals("Book 2", result.get(1).title());
    }

    @Test
    @DisplayName("Should handle empty entity list")
    void testEntityListToDtoListEmpty() {
        // Act
        List<BookRes1> result = bookMapper.entityListToDtoList(List.of());

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should handle null entity list")
    void testEntityListToDtoListNull() {
        // Act
        List<BookRes1> result = bookMapper.entityListToDtoList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should convert DTO list to entity list")
    void testDtoListToEntityList() {
        // Arrange
        BookReq1 bookReq1 = new BookReq1("Book 1", "Author 1", "ISBN001", 2024, true);
        BookReq1 bookReq2 = new BookReq1("Book 2", "Author 2", "ISBN002", 2023, false);

        // Act
        List<Book> result = bookMapper.dtoListToEntityList(List.of(bookReq1, bookReq2));

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
        assertEquals("Book 2", result.get(1).getTitle());
    }
}
