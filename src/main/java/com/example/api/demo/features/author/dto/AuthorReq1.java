package com.example.api.demo.features.author.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthorReq1(
              @NotBlank(message = "name is required") String name) {

}
