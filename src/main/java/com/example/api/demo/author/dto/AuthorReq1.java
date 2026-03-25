package com.example.api.demo.author.dto;

import java.util.List;

import com.example.api.demo.book.Book;

public record AuthorReq1(
        String name,
        List<Book> books) {

}
