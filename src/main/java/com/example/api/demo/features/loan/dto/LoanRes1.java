package com.example.api.demo.features.loan.dto;

import java.time.LocalDate;

import com.example.api.demo.features.book.dto.BookRes1;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoanRes1(
        @Schema(description = "Unique identifier for the loan") Long id,
        @Schema(description = "Book that is being loaned out") Long bookId,
        @Schema(description = "Loan date") LocalDate loanDate,
        @Schema(description = "Return date") LocalDate returnDate

) {
}
