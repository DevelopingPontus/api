package com.example.api.demo.loan.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.api.demo.book.Book;
import com.example.api.demo.book.dto.v1.BookRes1;
import com.example.api.demo.book.repository.BookRepository;
import com.example.api.demo.book.service.BookService;
import com.example.api.demo.generic.services.GenericService;
import com.example.api.demo.loan.Loan;
import com.example.api.demo.loan.dto.LoanReq1;
import com.example.api.demo.loan.dto.LoanRes1;
import com.example.api.demo.loan.mapper.LoanMapper;
import com.example.api.demo.loan.repository.LoanRepository;

public class LoanService extends GenericService<Loan, LoanReq1, LoanRes1> {
    private final BookRepository bookRepository;

    @Autowired
    public LoanService(LoanRepository repository, LoanMapper mapper, BookRepository bookRepository) {
        super(repository, mapper);
        this.bookRepository = bookRepository;
    }

    
    @Override
    public List<LoanRes1> save(List<LoanReq1> loanReq1) {
        List<Loan> loans = new ArrayList<>();
        for (LoanReq1 loanReq : loanReq1) {
            Book book = bookRepository.findById(loanReq.bookId()).get();
            if (!book.isAvailable()) {
                break;
            }
            book.setAvailable(false);
            Loan loan = mapper.dtoToEntity(loanReq);
            loan.setBook(book);
            loans.add(loan);
            repository.save(loan);
        }
        return mapper.entityListToDtoList(loans);
    }

}
