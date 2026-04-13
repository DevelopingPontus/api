package com.example.api.demo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a book is not available for loan.
 * Returns HTTP 400 Bad Request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAvailabilityException extends RuntimeException {

    public BookAvailabilityException(String message) {
        super(message);
    }

    public BookAvailabilityException(String message, Throwable cause) {
        super(message, cause);
    }
}