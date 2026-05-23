package com.example.api.demo.feature.loan;

import com.example.api.demo.feature.book.BookRepository;
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

@Service
public class LoanService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final BookService bookService;

    public LoanService(
            LoanRepository loanRepository,
            BookService bookService, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "loan")
    public List<Loan> getAll() {
        List<Loan> loans = loanRepository.findAll();
        if (!loans.isEmpty()) {
            return loans;
        } else {
            throw new LoanNotFoundException("No loans were found.");
        }
    }

    @Cacheable(value = "loan", key = "#loanId")
    public Loan getById(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            return loan.get();
        } else {
            throw new LoanNotFoundException("Loan with id " + loanId + " not found.");
        }
    }

    @CacheEvict(value = "loan", key = "#loanId")
    public void deleteById(Long loanId) {
        loanRepository.deleteById(loanId);
    }

    @CacheEvict(value = "loan", allEntries = true)
    public Loan save(Long bookId) {
        Book book = bookService.getById(bookId);
        if (!book.isAvailable()) {
            throw new BookNotOnLoanException("Book with id " + bookId + " is on loan.");
        }
        Loan newLoan = new Loan(book);
        book.setAvailable(false);
        loanRepository.save(newLoan);
        return newLoan;
    }

    @CacheEvict(value = "loan", key = "#loanId")
    public Loan update(Long loanId) {
        Optional<Loan> Loan = loanRepository.findById(loanId);
        if (Loan.isPresent()) {
            Loan loan = Loan.get();
            loan.setRetunDate(LocalDate.now());
            loanRepository.save(loan);
            Book book = loan.getBook();
            book.setAvailable(true);
            bookRepository.save(book);
            return loan;
        } else {
            throw new LoanNotFoundException("Loan with id " + loanId + " not found.");
        }
    }

}
