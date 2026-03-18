package com.example.api.demo.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

public record BookRequest2(
        @Null @Schema(description = "Book ID (is generatd)", example = "null") Long id,
        @NotBlank(message = "Title required") @Schema(description = "Book Title") String title,
        @NotBlank(message = "Description required") @Schema(description = "Book Description") String description,
        @NotBlank(message = "ISBN required") @Schema(description = "Book ISBN") String isbn,
        @Schema(description = "Book Publication Year") Integer year,
        @Schema(description = "Availability (is set to true on creation)", example = "true") boolean available) {

}
