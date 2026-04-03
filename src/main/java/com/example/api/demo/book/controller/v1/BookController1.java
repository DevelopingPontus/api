package com.example.api.demo.book.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.book.Book;
import com.example.api.demo.book.dto.v1.BookReq1;
import com.example.api.demo.book.dto.v1.BookRes1;
import com.example.api.demo.book.service.BookService;
import com.example.api.demo.generic.controllers.GenericController;

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
