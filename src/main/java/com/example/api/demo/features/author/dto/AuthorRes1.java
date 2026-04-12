package com.example.api.demo.features.author.dto;

import java.util.List;

import com.example.api.demo.features.book.dto.BookRes1;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthorRes1(
        @Schema(description = "Unique identifier for the book") Long id,
        @Schema(description = "Authors name") String name,
        @Schema(description = "Books of the author") List<BookRes1> books) {

}
