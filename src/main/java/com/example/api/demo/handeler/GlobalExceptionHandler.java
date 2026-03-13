package com.example.api.demo.handeler;

import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.MethodArgumentBuilder;

import com.example.api.demo.exception.BookNotFoundException;
import com.example.api.demo.exception.BooksNotFoundException;
import com.example.api.demo.exception.ValidationException;

// Spring
@ControllerAdvice
public class GlobalExceptionHandler {

    // Spring
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Spring
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(BookNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Spring
    @ExceptionHandler(BooksNotFoundException.class)
    public ResponseEntity<String> handleBooksNotFoundException(BooksNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Spring
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentBuilder ex) {
        return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
    }

    //Spring
    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<String> handleURISyntaxException(URISyntaxException ex) {
        return new ResponseEntity<>("URI syntax error", HttpStatus.BAD_REQUEST);
    }

    // Spring
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Jikes error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
