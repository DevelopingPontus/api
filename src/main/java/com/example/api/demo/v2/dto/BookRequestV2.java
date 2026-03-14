package com.example.api.demo.v2.dto;

public record BookRequestV2(
                Long id,
                String title,
                String description,
                String isbn,
                int year) {

}
