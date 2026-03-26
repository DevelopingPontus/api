package com.example.api.demo.book;

import com.example.api.demo.author.Author;
import com.example.api.demo.generic.interfaces.EntityInterface;

import jakarta.persistence.*;

@Entity
public class Book implements EntityInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @ManyToOne
    private Author author;
    private String isbn;
    private int publishedYear;
    private boolean available;

    // Constructors, getters, and setters

    public Book() {
    }

    public Book(String title, String isbn, int publishedYear) {
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.available = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}