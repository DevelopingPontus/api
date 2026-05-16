package com.example.api.demo.feature.loan.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;

public record LoanReqestV1(
        @PositiveOrZero @Schema(description = "Id of book that is loaned") Long bookId) {
}
