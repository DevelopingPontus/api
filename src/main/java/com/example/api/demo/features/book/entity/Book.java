package com.example.api.demo.features.book.entity;

import com.example.api.demo.features.author.entity.Author;
import com.example.api.demo.common.interfaces.EntityInterface;
import com.example.api.demo.features.loan.entity.Loan;

import jakarta.persistence.*;

@Entity
public class Book implements EntityInterface {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    private String title;
    @ManyToOne
    private Author author;
    private String isbn;
    private int publishedYear;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BookAvailability availability;

    @OneToOne(mappedBy = "book")
    private Loan loan;

    // Constructors, getters, and setters

    public Book() {
    }

    public Book(String title, String isbn, int publishedYear) {
        this.title = title;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        return availability.isAvailable();
    }

    public void setAvailable(boolean available) {
        if (this.availability == null) {
            this.availability = new BookAvailability(this, available);
        } else {
            this.availability.setAvailable(available);
        }
    }

    public BookAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(BookAvailability availability) {
        this.availability = availability;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

}