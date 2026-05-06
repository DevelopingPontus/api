package com.example.api.demo.feature.loan.v1;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoanResponseV1(
        @Schema(description = "Unique identifier for the loan") Long id,
        @Schema(description = "Book that is being loaned out") Long bookId,
        @Schema(description = "Loan date") LocalDate loanDate,
        @Schema(description = "Return date") LocalDate returnDate
) {
}
