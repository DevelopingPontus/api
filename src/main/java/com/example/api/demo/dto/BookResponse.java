package com.example.api.demo.dto;


public record BookResponse(
        Long id,
        String title,
        String description,
        String isbn,
        int year) {

}
