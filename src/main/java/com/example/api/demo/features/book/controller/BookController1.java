package com.example.api.demo.features.book.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.features.book.entity.Book;
import com.example.api.demo.features.book.dto.BookReq1;
import com.example.api.demo.features.book.dto.BookRes1;
import com.example.api.demo.features.book.service.BookService;
import com.example.api.demo.common.controllers.GenericController;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController1 extends GenericController<Book, BookReq1, BookRes1> {

    @Autowired
    public BookController1(BookService service) {
        super(service, "v1");
    }
    // No need to override methods if they are already defined in the
    // GenericController
}
