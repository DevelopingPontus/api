package com.example.api.demo.book.dto.v1;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record BookReq1(
       @NotBlank(message = "title is required") @Schema(description = "Title of the book") String title,
       @NotBlank(message = "author is required") @Schema(description = "Author of the book") String author,
       @NotBlank(message = "isbn is required") @Schema(description = "ISBN number of the book") String isbn,
       @Schema(description = "Year the book was published") int publishedYear) {
}
