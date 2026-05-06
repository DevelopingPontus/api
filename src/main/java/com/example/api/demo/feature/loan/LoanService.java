package com.example.api.demo.feature.loan;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.api.demo.common.exception.BookAvailabilityException;
import com.example.api.demo.common.exception.BookNotOnLoanException;
import com.example.api.demo.feature.book.Book;
import com.example.api.demo.feature.book.BookRepository;
import com.example.api.demo.feature.book.BookService;
import com.example.api.demo.feature.book.bookAvailability.BookAvailabilityService;
import com.example.api.demo.feature.loan.v1.LoanMapperV1;
import com.example.api.demo.feature.loan.v1.LoanReqestV1;
import com.example.api.demo.feature.loan.v1.LoanResponseV1;

import jakarta.transaction.Transactional;

@Service
public class LoanService {
    private final LoanRepository lonaRepository;
    private final LoanMapperV1 mapper;
    private final BookService bookService;
    private final BookAvailabilityService bookAvailabilityService;

    public LoanService(
            LoanRepository lonaRepository,
            LoanMapperV1 mapper,
            BookService bookService,
            BookAvailabilityService bookAvailabilityService
        ) {
        this.lonaRepository = lonaRepository;
        this.mapper = mapper;
        this.bookService = bookService;
        this.bookAvailabilityService = bookAvailabilityService;
    }

    @Cacheable(value = "all")
    public List<LoanResponseV1> getAll() {
        return mapper.entityListToDtoList(lonaRepository.findAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public LoanResponseV1 getById(Long id) {
        return mapper.entityToDto(lonaRepository.findById(id).orElse(null));
    }

    @Transactional
    public Loan save(Long bookId) {
        Book book = bookService.getById(bookId);
        Optional<Loan> loan = lonaRepository.findLastByBookIdOptional(bookId);
        if (loan.isPresent() && loan.get().getRetunDate() == null) {
            throw new BookNotOnLoanException("Book with id " + bookId + " is on loan.");
        }
            Loan newLoan = new Loan(book);
            lonaRepository.save(newLoan);
            return newLoan;
        
    }
    
    public Loan update(Long bookId) {
        Optional<Loan> loan = lonaRepository.findLastByBookIdOptional(bookId);
        if (loan.isPresent() || loan.get().getRetunDate() == null) {
            loan.get().setRetunDate(LocalDate.now());
            lonaRepository.save(loan.get());
            return loan.get();
        }
        throw new BookNotOnLoanException("Book with id " + bookId + " is not on loan.");
    }
    
    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        lonaRepository.deleteById(id);
    }
}
