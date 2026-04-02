package com.example.api.demo.loan.dto;

import java.time.LocalDate;

import com.example.api.demo.book.dto.v1.BookRes1;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoanRes1(
                @Schema(description = "Unique identifier for the loan") Long id,
                @Schema(description = "Book that is being loaned out") BookRes1 book,
                @Schema(description = "Loan date") LocalDate loanDate,
                @Schema(description = "Return date") LocalDate returnDate

) {
}
