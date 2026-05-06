package com.example.api.demo.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookNotOnLoanException extends RuntimeException {
    public BookNotOnLoanException(String message) {
        super(message);
    }
}
