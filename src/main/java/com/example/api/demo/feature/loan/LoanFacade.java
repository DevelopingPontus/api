package com.example.api.demo.feature.loan;

import com.example.api.demo.feature.loan.v1.LoanMapperV1;
import com.example.api.demo.feature.loan.v1.LoanReqestV1;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.api.demo.feature.loan.v1.LoanResponseV1;


@Component
public class LoanFacade {

    private final LoanMapperV1 loanMapperV1;
    private final LoanService loanService;

    public LoanFacade(LoanMapperV1 loanMapperV1, LoanService loanService) {
        this.loanMapperV1 = loanMapperV1;
        this.loanService = loanService;
    }

    public List<LoanResponseV1> getAll() {
        return loanMapperV1.entityListToDtoList(loanService.getAll());
    }

    public LoanResponseV1 getById(Long loanId) {
        Loan loan = (loanService.getById(loanId));
        return loanMapperV1.entityToDto(loan);
    }

    @Transactional
    public void deleteById(Long loanId) {
        loanService.deleteById(loanId);
    }

    @Transactional
    public LoanResponseV1 save(LoanReqestV1 loanRequest) {
        Loan loan = loanService.save(loanRequest.bookId());
        return loanMapperV1.entityToDto(loan);
    }

    @Transactional
    public LoanResponseV1 update(Long bookId) {
        Loan loan = loanService.update(bookId);
        return loanMapperV1.entityToDto(loan);
    }

}
