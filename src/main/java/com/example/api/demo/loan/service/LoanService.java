package com.example.api.demo.loan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.api.demo.book.repository.BookRepository;
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

    // TODO: Make logic for making a loan, with regard to book.
    @Override
    public List<LoanRes1> save(List<LoanReq1> loanReq1) {
        return null;
    }

}
