package com.example.api.demo.features.book;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookRes1(
        @Schema(description = "Unique identifier for the book") Long id,
        @Schema(description = "Title of the book") String title,
        @Schema(description = "Author of the book") String author,
        @Schema(description = "ISBN number of the book") String isbn,
        @Schema(description = "Year the book was published") int publishedYear,
        @Schema(description = "Availability status of the book") boolean available) {
}
