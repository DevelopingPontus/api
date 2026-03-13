package com.example.api.demo.v2.dto;

import org.springframework.stereotype.Component;

@Component
public record BookRequestV2(
        Long id,
        String title,
        String description,
        String isbn,
        int year,
        boolean available,
        String version) {

}
