package com.example.api.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// lombok.Data generate getters and setters.
@lombok.Data
@Entity
@Table
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "year")
    private int year;

    public Step() {

    }
    
    public Step(String title, String description, String isbn, int year) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.year = year;
    }

    public Step(Long id, String title, String description, String isbn, int year) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.year = year;
    }



    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", name='" + title + '/' +
                ", description='" + description + '/' +
                "}";
    }

}
