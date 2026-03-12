package com.example.api.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
    private Long id;

    // Jakarta
    @NotBlank(message = "Title required")
    private String title;

    // Jakarta
    @NotBlank(message = "Description required")
    private String description;

    // Jakarta
    @NotBlank(message = "ISBN required")
    private String isbn;

    // Jakarta
    @Min(100)
    // Getting current year would be better
    @Max(2026)
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
