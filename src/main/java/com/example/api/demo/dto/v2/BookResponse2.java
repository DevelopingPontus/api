package com.example.api.demo.dto.v2;

public record BookResponse2(
                Long id,
                String title,
                String description,
                String isbn,
                boolean available) {

}
