package com.example.api.demo.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record BookRequest2(
                @NotBlank(message = "Title required") @Schema(description = "Book Title") String title,
                @NotBlank(message = "Description required") @Schema(description = "Book Description") String description,
                @NotBlank(message = "ISBN required") @Schema(description = "Book ISBN") String isbn) {
}
