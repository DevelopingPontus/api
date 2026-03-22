package com.example.api.demo.book.dto.v1;


public record BookDTO(Long id, String title, String author, String isbn, int publishedYear) {
}
