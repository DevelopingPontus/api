package com.example.api.demo.common.exception;

import java.net.URISyntaxException;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>("Invalid request parameters: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // URI
    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<String> handleURISyntaxException(URISyntaxException ex) {
        return new ResponseEntity<>("URI syntax error", HttpStatus.BAD_REQUEST);
    }

    // Book not available - 400 Bad Request
    @ExceptionHandler(BookAvailabilityException.class)
    public ResponseEntity<String> handleBookNotAvailable(BookAvailabilityException ex) {
        return new ResponseEntity<>("Book is not available for loan: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // General Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleEntityNotFoundException(NoSuchElementException ex) {
        return new ResponseEntity<>("Entity not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
