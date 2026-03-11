package com.example.api.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.api.demo.dto.StepRequest;
import com.example.api.demo.dto.StepResponse;
import com.example.api.demo.entity.Step;
import com.example.api.demo.repository.StepRepository;
import com.example.api.demo.service.mapper.StepMapper;

@Service
public class StepService {
    private final StepRepository repository;
    private final StepMapper mapper;

    public StepService(StepRepository repository, StepMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<StepResponse> getAll() {
        List<Step> entities = repository.findAll();
        return entities.stream().map(mapper::toDto).toList();
    }

    public void create(StepRequest request) {
        Step entity = mapper.toEntity(request);
        repository.save(entity);
    }

}
