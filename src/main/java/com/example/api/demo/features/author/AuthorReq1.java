package com.example.api.demo.features.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorReq1(
              @NotBlank(message = "name is required") String name) {

}
