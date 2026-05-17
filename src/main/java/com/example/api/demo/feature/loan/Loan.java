package com.example.api.demo.feature.loan;

import java.io.Serializable;
import java.time.LocalDate;

import com.example.api.demo.feature.book.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDate loanDate;

    private LocalDate returnDate;

    public Loan() {
    }

    public Loan(Book book) {
        this.book = book;
        this.loanDate = LocalDate.now();
        this.returnDate = null;
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
        return returnDate;
    }

    public void setRetunDate(LocalDate retunDate) {
        this.returnDate = retunDate;
    }

}
