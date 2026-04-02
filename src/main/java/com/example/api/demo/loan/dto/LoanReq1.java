package com.example.api.demo.loan.dto;

import java.time.LocalDate;


import io.swagger.v3.oas.annotations.media.Schema;

public record LoanReq1(
        @Schema(description = "Unique identifier for the loan") Long id,
        @Schema(description = "Book that is being loaned out") Long bookId,
        @Schema(description = "Loan date") LocalDate loanDate,
        @Schema(description = "Return date") LocalDate returnDate) {
}
