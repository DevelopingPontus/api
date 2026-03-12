package com.example.api.demo.exception;

public class BooksNotFoundException extends RuntimeException {
    public BooksNotFoundException(String message) {
        super(message);
    }
}