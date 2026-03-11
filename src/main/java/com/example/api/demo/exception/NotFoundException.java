package com.example.api.demo.exception;

public class NotFoundException extends Exception {
    private final Long id;

    public NotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

