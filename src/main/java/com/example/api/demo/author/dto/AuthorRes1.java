package com.example.api.demo.author.dto;

import java.util.List;

import com.example.api.demo.book.Book;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthorRes1(
                @Schema(description = "Unique identifier for the book") Long id,
                @Schema(description = "Authors name") String name,
                @Schema(description = "Books of the author") List<Book> books) {

}
