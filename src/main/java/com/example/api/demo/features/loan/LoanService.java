package com.example.api.demo.features.loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.api.demo.features.book.Book;
import com.example.api.demo.features.book.BookRepository;
import com.example.api.demo.features.book.bookAvailability.BookAvailabilityService;
import com.example.api.demo.common.exception.BookAvailabilityException;

import jakarta.transaction.Transactional;

@Service
public class LoanService {
    private final LoanRepository repository;
    private final LoanMapper mapper;
    private final BookRepository bookRepository;
    private final BookAvailabilityService bookAvailabilityService;

    public LoanService(LoanRepository repository, LoanMapper mapper, BookRepository bookRepository,
            BookAvailabilityService bookAvailabilityService) {
        this.repository = repository;
        this.mapper = mapper;
        this.bookRepository = bookRepository;
        this.bookAvailabilityService = bookAvailabilityService;
    }

    @Cacheable(value = "all")
    public List<LoanRes1> getAll() {
        return mapper.entityListToDtoList(repository.findAll());
    }

    @Cacheable(value = "byId", key = "#id")
    public LoanRes1 getById(Long id) {
        return mapper.entityToDto(repository.findById(id).orElse(null));
    }

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

    @CacheEvict(value = "byId", allEntries = true)
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

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
