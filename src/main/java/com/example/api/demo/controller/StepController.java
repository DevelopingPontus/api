package com.example.api.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.StepRequest;
import com.example.api.demo.dto.StepResponse;
import com.example.api.demo.service.StepService;

// RestController tells Spring Boot to treat the controller as a RESTful API.
// RequestMapping tells Spring Boot to map the controller to a specific URL.
public class StepController {
    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping
    public List<StepResponse> getAll() {
        create("test", "test", "test", 1234567890);
        return stepService.getAll();
    }

    @PostMapping
    public void create(String title, String description, String isbn, int year) {
        StepRequest request = new StepRequest(null, title, description, isbn, year);
        stepService.create(request);
    }

}
