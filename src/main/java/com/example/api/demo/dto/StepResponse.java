package com.example.api.demo.dto;

public record StepResponse(
        Long id,
        String title,
        String description,
        String isbn,
        int year) {

}
