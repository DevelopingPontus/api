package com.example.api.demo.dto;

public record BookResponseV2(
                Long id,
                String title,
                String description,
                String isbn,
                int year,
                boolean available) {

}
