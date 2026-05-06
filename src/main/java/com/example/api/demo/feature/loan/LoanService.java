package com.example.api.demo.feature.loan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.api.demo.common.exception.BookNotOnLoanException;
import com.example.api.demo.common.exception.LoanNotFoundException;
import com.example.api.demo.feature.book.Book;
import com.example.api.demo.feature.book.BookService;

import jakarta.transaction.Transactional;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookService bookService;

    public LoanService(
            LoanRepository loanRepository,
            BookService bookService
        ) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
    }

    @Cacheable(value = "all")
    public List<Loan> getAll() {
        List<Loan> loans = loanRepository.findAll();
        if (!loans.isEmpty()) {
            return loans;
        } else {
            throw new LoanNotFoundException("No loans were found.");
        }
    }

    @Cacheable(value = "byId", key = "#id")
    public Optional<Loan> getById(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            return loan;
        } else {
            throw new LoanNotFoundException("Loan with id " + loanId + " not found.");
        }
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }

    @Transactional
    public Loan save(Long bookId) {
        Book book = bookService.getById(bookId).get();
        Optional<Loan> loan = loanRepository.findLastByBookIdOptional(bookId);
        if (loan.isPresent() && loan.get().getRetunDate() == null) {
            throw new BookNotOnLoanException("Book with id " + bookId + " is on loan.");
        }
            Loan newLoan = new Loan(book);
            loanRepository.save(newLoan);
            return newLoan;
    }
    
    public Loan update(Long bookId) {
        Optional<Loan> loan = loanRepository.findLastByBookIdOptional(bookId);
        if (loan.isPresent() || loan.get().getRetunDate() == null) {
            loan.get().setRetunDate(LocalDate.now());
            loanRepository.save(loan.get());
            return loan.get();
        }
        throw new BookNotOnLoanException("Book with id " + bookId + " is not on loan.");
    }
    
}
