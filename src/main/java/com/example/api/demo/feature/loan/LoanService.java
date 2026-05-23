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

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookService bookService;

    public LoanService(
            LoanRepository loanRepository,
            BookService bookService) {
        this.loanRepository = loanRepository;
        this.bookService = bookService;
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

    @Cacheable(value = "loan", key = "#bookId")
    public Loan getById(Long loanId) {
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            return loan.get();
        } else {
            throw new LoanNotFoundException("Loan with id " + loanId + " not found.");
        }
    }

    @CacheEvict(value = "loan", key = "#bookId")
    public void deleteById(Long bookId) {
        loanRepository.deleteById(bookId);
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

    @CacheEvict(value = "loan", key = "#bookId")
    public Loan update(Long bookId) {
        Loan loan = loanRepository.findLastByBookId(bookId);
        if (loan.getRetunDate() == null) {
            loan.setRetunDate(LocalDate.now());
            loanRepository.save(loan);
            loan.getBook().setAvailable(true);
            return loan;
        }
        throw new BookNotOnLoanException("Book with id " + bookId + " is not on loan.");
    }

}
