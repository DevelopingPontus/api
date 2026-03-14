package com.example.api.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@lombok.Data
@Entity
public class BookStatus {
    @Id
    @jakarta.validation.constraints.Null
    private Long id;

    @Schema(description = "Book availability status", example = "true")
    private boolean available;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public BookStatus() {
    }

    public BookStatus(boolean available) {
        this.available = available;
    }

    public BookStatus(Long id, boolean available) {
        this.id = id;
        this.available = available;
    }

    @Override
    public String toString() {
        return "BookAvailableStatus{" +
                "available=" + available +
                '}';
    }
}
