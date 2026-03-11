package com.example.api.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.BookRequest;
import com.example.api.demo.dto.BookResponse;
import com.example.api.demo.service.BookService;

// RestController tells Spring Boot to treat the controller as a RESTful API.
// RequestMapping tells Spring Boot to map the controller to a specific URL.
@RestController
@RequestMapping("api/v1/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookResponse> getAll() {
        create("Jag", "dansar", 2019, "12345678");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BookResponse getOne(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public void create(String title, String description, int year, String isbn) {
        BookRequest request = new BookRequest(null, title, description, isbn, year);
        service.create(request);
    }

}
