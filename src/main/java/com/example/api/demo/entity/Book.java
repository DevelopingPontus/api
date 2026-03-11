package com.example.api.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// lombok.Data generate getters and setters.
@lombok.Data
@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Description required")
    private String description;

    @NotBlank(message = "ISBN required")
    private String isbn;

    @Positive
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + title + '/' +
                ", description='" + description + '/' +
                "}";
    }

}
