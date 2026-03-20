package com.example.api.demo.dto.v1;

public record BookResponse(
        Long id,
        String title,
        String description,
        String isbn) {

}
