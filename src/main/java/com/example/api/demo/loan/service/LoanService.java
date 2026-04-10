package com.example.api.demo.loan.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.api.demo.book.Book;
import com.example.api.demo.book.repository.BookRepository;
import com.example.api.demo.generic.services.GenericService;
import com.example.api.demo.loan.Loan;
import com.example.api.demo.loan.dto.LoanReq1;
import com.example.api.demo.loan.dto.LoanRes1;
import com.example.api.demo.loan.mapper.LoanMapper;
import com.example.api.demo.loan.repository.LoanRepository;

import jakarta.transaction.Transactional;

@Service
public class LoanService extends GenericService<Loan, LoanReq1, LoanRes1> {
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository repository, LoanMapper mapper, BookRepository bookRepository) {
        super(repository, mapper);
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public List<LoanRes1> save(List<LoanReq1> loanReq1) {
        List<LoanRes1> results = new ArrayList<>();

        for (LoanReq1 loanReq : loanReq1) {
            Optional<Book> bookOpt = bookRepository.findById(loanReq.bookId());
            if (bookOpt.isEmpty() || !bookOpt.get().isAvailable()) {
                continue; // Skip invalid loans
            }

            Book book = bookOpt.get();
            book.setAvailable(false);
            bookRepository.save(book);

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
                // Handle the case where the book is not found
                throw new ResourceAccessException("Book with id " + id + " not found");
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

            entity.setRetunDate(LocalDate.now());
            repository.save(entity);

            res.add(mapper.entityToDto(entity));
        
        return res;
    }

}
