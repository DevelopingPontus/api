package com.example.api.demo.dto.v2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthorRequest2(

        @Schema(description = "Authors first name", example = "John") @NotBlank(message = "First name cannot be blank") String firstName,
        @Schema(description = "Authors last name", example = "Doe") @NotBlank(message = "Last name cannot be blank") String lastName) {
}
