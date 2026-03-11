package com.example.api.demo.service.mapper;

import org.springframework.stereotype.Component;

import com.example.api.demo.dto.StepRequest;
import com.example.api.demo.dto.StepResponse;
import com.example.api.demo.entity.Step;

@Component
public class StepMapper {
    public StepResponse toDto(Step entity) {
        return new StepResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getIsbn(),
                entity.getYear());
    }

    public Step toEntity(StepRequest request) {
        return new Step(
                request.id(),
                request.title(),
                request.description(),
                request.isbn(),
                request.year());
    }
}
