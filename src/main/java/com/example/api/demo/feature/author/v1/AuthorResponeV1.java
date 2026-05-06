package com.example.api.demo.feature.author.v1;

import java.util.List;

import com.example.api.demo.feature.book.v1.BookResponseV1;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthorResponeV1(
        @Schema(description = "Unique identifier for the book") Long id,
        @Schema(description = "Authors name") String name,
        @Schema(description = "Books of the author") List<BookResponseV1> books) {

}
