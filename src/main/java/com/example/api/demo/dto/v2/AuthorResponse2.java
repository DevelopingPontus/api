package com.example.api.demo.dto.v2;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthorResponse2(
        Long id,
        @Schema(description = "Authors first name") String firstName,
        @Schema(description = "Authors last name") String lastName) {

}
