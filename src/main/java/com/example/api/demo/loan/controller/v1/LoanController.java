package com.example.api.demo.loan.controller.v1;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.generic.controllers.GenericController;
import com.example.api.demo.generic.wrappers.GenericWrapperResponse;
import com.example.api.demo.loan.Loan;
import com.example.api.demo.loan.dto.LoanReq1;
import com.example.api.demo.loan.dto.LoanRes1;
import com.example.api.demo.loan.service.LoanService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/loans")
@Tag(name = "Loans", description = "Operations related to loans")
public class LoanController extends GenericController<Loan, LoanReq1, LoanRes1> {
    LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        super(loanService, "v1");
    }

    @PutMapping("/{id}")

    public ResponseEntity<GenericWrapperResponse<LoanRes1>> returnLoan(@RequestBody @PathVariable LoanReq1 id) {
        List<LoanRes1> updatedDto = loanService.update(id);
        GenericWrapperResponse<LoanRes1> wrapperResponse = new GenericWrapperResponse<>(updatedDto, version);
        return ResponseEntity.status(201).body(wrapperResponse);
    }
}
