package com.example.api.demo.v2.dto;

import org.springframework.stereotype.Component;

import com.example.api.demo.v1.dto.BookResponseV1;

@Component
public record BookResponseV2(
        Long id,
        String title,
        String description,
        String isbn,
        int year,
        boolean available,
        String version) {

}
