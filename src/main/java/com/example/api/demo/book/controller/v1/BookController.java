package com.example.api.demo.book.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.api.demo.book.dto.v1.BookDTO;
import com.example.api.demo.book.entity.Book;
import com.example.api.demo.book.mapper.BookMapper;
import com.example.api.demo.book.service.BookService;
import com.example.api.demo.generic.controllers.GenericController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController extends GenericController<Book, BookDTO> {

    @Autowired
    public BookController(BookService service, BookMapper mapper) {
        super(service, mapper);
    }
    // No need to override methods if they are already defined in the
    // GenericController
}
