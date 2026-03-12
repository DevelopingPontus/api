package com.example.api.demo.dto;

public record BookRequest(
                Long id,
                String title,
                String description,
                String isbn,
                int year) {

}
