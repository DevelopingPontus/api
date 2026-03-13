package com.example.api.demo.v1.dto;

public record BookRequestV1(
        Long id,
        String title,
        String description,
        String isbn,
        int year) {

}
