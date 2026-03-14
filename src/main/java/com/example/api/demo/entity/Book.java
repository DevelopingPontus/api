package com.example.api.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

// lombok.Data generate getters and setters.
@lombok.Data
// Jakarta
@Entity
@Table
public class Book {
    // Jakarta
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @jakarta.validation.constraints.Null
    // OpenApi
    @Schema(description = "ID of the book (should be null when creating a new book)", example = "null")
    private Long id;

    // Jakarta
    @NotBlank(message = "Title required")
    // OpenApi
    @Schema(description = "Title")
    private String title;

    // Jakarta
    @NotBlank(message = "Description required")
    // OpenApi
    @Schema(description = "Description")
    private String description;

    // Jakarta
    @NotBlank(message = "ISBN required")
    // OpenApi
    @Schema(description = "ISBN")
    private String isbn;

    private int year;

    public Book() {

    }

    public Book(String title, String description, String isbn, int year) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.year = year;
    }

    public Book(Long id, String title, String description, String isbn, int year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.year = year;
    }

    // Java
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + title + '/' +
                ", description='" + description + '/' +
                "}";
    }

}
