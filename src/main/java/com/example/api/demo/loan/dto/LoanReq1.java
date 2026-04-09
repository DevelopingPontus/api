package com.example.api.demo.loan.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record LoanReq1(
                @Schema(description = "Book that is being loaned out") Long bookId) {
}
