package com.example.api.demo.features.author.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.features.author.dto.AuthorReq1;
import com.example.api.demo.features.author.dto.AuthorRes1;
import com.example.api.demo.features.author.entity.Author;
import com.example.api.demo.features.author.service.AuthorService;
import com.example.api.demo.common.controllers.GenericController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/authors")
@Tag(name = "Author Controller", description = "Operations about Authors")
public class AuthorController1 extends GenericController<Author, AuthorReq1, AuthorRes1> {

    protected AuthorController1(AuthorService service) {
        super(service, "v1");
    }

}
