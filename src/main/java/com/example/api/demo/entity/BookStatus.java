package com.example.api.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@lombok.Data
@Entity
@Table
public class BookStatus {
    @Id
    private Long id;

    @Schema(description = "Book availability status", example = "null")
    private boolean available;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public BookStatus(Long id) {
        this.id = id;
        this.available = true;
    }



    @Override
    public String toString() {
        return "BookAvailableStatus{" +
                "available=" + available +
                '}';
    }
}
