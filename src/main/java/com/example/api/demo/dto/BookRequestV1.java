package com.example.api.demo.dto;

public record BookRequestV1(
        Long id,
        String title,
        String description,
        String isbn,
        int year) {

}
