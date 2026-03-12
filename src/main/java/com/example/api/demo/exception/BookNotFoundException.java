package com.example.api.demo.exception;

public class BookNotFoundException extends RuntimeException {
    private final Long id;

    public BookNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
