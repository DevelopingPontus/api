package com.example.api.demo.book.dto.v1;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookDTO(
        @Schema(description = "Unique identifier for the book. When adding new book it should be null. When updating it should not be null.", example = "null") Long id,
        @Schema(description = "Title of the book") String title,
        @Schema(description = "Author of the book") String author,
        @Schema(description = "ISBN number of the book") String isbn,
        @Schema(description = "Year the book was published") int publishedYear) {
}
