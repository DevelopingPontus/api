package com.example.api.demo.features.loan;

import java.time.LocalDate;

import com.example.api.demo.features.book.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Book book;

    private LocalDate loanDate;

    private LocalDate retunDate;

    public Loan() {
    }

    public Loan(Long id, Book book) {
        this.id = id;
        this.book = book;
        this.loanDate = LocalDate.now();
        this.retunDate = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getRetunDate() {
        return retunDate;
    }

    public void setRetunDate(LocalDate retunDate) {
        this.retunDate = retunDate;
    }

}
