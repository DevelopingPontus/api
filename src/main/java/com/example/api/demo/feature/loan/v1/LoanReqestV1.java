package com.example.api.demo.feature.loan.v1;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoanReqestV1(
        @Schema(description = "Book that is being loaned out") Long bookId) {
}
