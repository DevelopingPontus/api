package com.example.api.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

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

    @Schema(description = "Publication year")
    private int year;

    @Schema(description = "Available")
    private boolean available;

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

    public Book(Long id, String title, String description, String isbn, int year, boolean available) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.year = year;
        this.available = available;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
