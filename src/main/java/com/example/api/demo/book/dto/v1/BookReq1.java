package com.example.api.demo.book.dto.v1;

import com.example.api.demo.author.Author;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookReq1(
        @Schema(description = "Title of the book") String title,
        @Schema(description = "Author of the book") Author author,
        @Schema(description = "ISBN number of the book") String isbn,
        @Schema(description = "Year the book was published") int publishedYear) {
}
