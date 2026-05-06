package com.example.api.demo.feature.author.v1;

import jakarta.validation.constraints.NotBlank;

public record AuthorRequestV1(
              @NotBlank(message = "name is required") String name) {

}
