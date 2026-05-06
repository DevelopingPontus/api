package com.example.api.demo.feature.loan;

import com.example.api.demo.feature.loan.v1.LoanMapperV1;
import com.example.api.demo.feature.loan.v1.LoanReqestV1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import com.example.api.demo.common.exception.BookAvailabilityException;
import com.example.api.demo.feature.book.Book;
import com.example.api.demo.feature.book.BookService;
import com.example.api.demo.feature.book.bookAvailability.BookAvailabilityService;
import com.example.api.demo.feature.loan.v1.LoanResponseV1;

import jakarta.transaction.Transactional;

@Component
public class LoanFacade {

    private final LoanMapperV1 loanMapperV1;
    private final LoanService loanService;

    public LoanFacade(LoanMapperV1 loanMapperV1, LoanService loanService, BookService bookService) {
        this.loanMapperV1 = loanMapperV1;
        this.loanService = loanService;
    }

    @Cacheable(value = "all")
    public List<LoanResponseV1> getAll() {
        return loanMapperV1.entityListToDtoList(loanService.getAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public LoanResponseV1 getById(Long id) {
        return loanMapperV1.entityToDto(loanService.getById(id).orElse(null));
    }

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long loanId) {
        loanService.deleteById(loanId);
    }

    @Transactional
    public LoanResponseV1 save(Long bookId) {
        Loan loan = loanService.save(bookId);
        return loanMapperV1.entityToDto(loan);
    }

    public LoanResponseV1 update(Long bookId) {
        Loan loan = loanService.update(bookId);
        return loanMapperV1.entityToDto(loan);
    }

}
