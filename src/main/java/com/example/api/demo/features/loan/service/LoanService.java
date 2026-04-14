package com.example.api.demo.features.loan.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.api.demo.features.book.entity.Book;
import com.example.api.demo.features.book.repository.BookRepository;
import com.example.api.demo.features.book.service.BookAvailabilityService;
import com.example.api.demo.common.exception.BookAvailabilityException;
import com.example.api.demo.common.services.GenericService;
import com.example.api.demo.features.loan.entity.Loan;
import com.example.api.demo.features.loan.dto.LoanReq1;
import com.example.api.demo.features.loan.dto.LoanRes1;
import com.example.api.demo.features.loan.mapper.LoanMapper;
import com.example.api.demo.features.loan.repository.LoanRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanService extends GenericService<Loan, LoanReq1, LoanRes1> {
    private final BookRepository bookRepository;
    private final BookAvailabilityService bookAvailabilityService;

    @Autowired
    public LoanService(LoanRepository repository, LoanMapper mapper, BookRepository bookRepository,
            BookAvailabilityService bookAvailabilityService) {
        super(repository, mapper);
        this.bookRepository = bookRepository;
        this.bookAvailabilityService = bookAvailabilityService;
    }

    @Override
    @Transactional
    public List<LoanRes1> save(List<LoanReq1> loanReq1) {
        List<LoanRes1> results = new ArrayList<>();

        for (LoanReq1 loanReq : loanReq1) {
            // Use pessimistic locking to prevent race conditions
            Optional<Book> bookOpt = bookRepository.findByIdWithLock(loanReq.bookId());
            if (bookOpt.isEmpty()) {
                continue; // Skip invalid loans
            }

            Book book = bookOpt.get();

            // Check availability again under lock to prevent race condition
            if (!book.isAvailable()) {
                Long bookId = book.getId();
                throw new BookAvailabilityException("Book with id " + bookId + " is not available");
            }

            // Mark as unavailable atomically within the transaction
            book.setAvailable(false);
            bookRepository.save(book);

            // Invalidate cache when marking book as unavailable
            bookAvailabilityService.updateAvailabilityStatus(book.getId(), false);

            // Create and save loan
            Loan loan = mapper.dtoToEntity(loanReq);
            loan.setBook(book);
            loan.setLoanDate(LocalDate.now());

            repository.save(loan);
            results.add(mapper.entityToDto(loan));
        }

        return results;
    }

    @Override
    public List<LoanRes1> update(Long id) {
        List<LoanRes1> res = new ArrayList<>();

        Optional<Loan> entityOptional = repository.findById(id);

        if (!entityOptional.isPresent()) {
            // Handle the case where the loan is not found
            throw new ResourceAccessException("Loan with id " + id + " not found");
        }

        Loan entity = entityOptional.get();
        Optional<Book> bookOptional = bookRepository.findById(entity.getBook().getId());

        if (!bookOptional.isPresent()) {
            // Handle the case where the book is not found
            throw new ResourceAccessException("Book with id " + entity.getBook().getId() + " not found");
        }

        Book book = bookOptional.get();
        book.setAvailable(true);
        bookRepository.save(book);

        // Invalidate cache when marking book as available again
        bookAvailabilityService.updateAvailabilityStatus(book.getId(), true);

        entity.setRetunDate(LocalDate.now());
        repository.save(entity);

        res.add(mapper.entityToDto(entity));

        return res;
    }

}
