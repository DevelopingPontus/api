package com.example.api.demo.features.loan.controller;

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

import com.example.api.demo.common.controllers.GenericController;
import com.example.api.demo.common.wrappers.GenericWrapperResponse;
import com.example.api.demo.features.loan.entity.Loan;
import com.example.api.demo.features.loan.dto.LoanReq1;
import com.example.api.demo.features.loan.dto.LoanRes1;
import com.example.api.demo.features.loan.service.LoanService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/loans")
@Tag(name = "Loans", description = "Operations related to loans")
public class LoanController extends GenericController<Loan, LoanReq1, LoanRes1> {

    @Autowired
    public LoanController(LoanService loanService) {
        super(loanService, "v1");
    }

}
