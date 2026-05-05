package com.example.api.demo.features.loan;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoanRes1(
        @Schema(description = "Unique identifier for the loan") Long id,
        @Schema(description = "Book that is being loaned out") Long bookId,
        @Schema(description = "Loan date") LocalDate loanDate,
        @Schema(description = "Return date") LocalDate returnDate
) {
}
