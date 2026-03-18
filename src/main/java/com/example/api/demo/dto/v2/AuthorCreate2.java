package com.example.api.demo.dto.v2;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthorCreate2(
        @Schema(description = "Authors first name") @NotBlank(message = "First name cannot be blank") String firstName,
        @Schema(description = "Authors last name") @NotBlank(message = "Last name cannot be blank") String lastName,
        @Schema(description = "Author's date of birth (yyyy-mm-dd)", example = "1850-02-25") LocalDate birthDate) {
    
}
