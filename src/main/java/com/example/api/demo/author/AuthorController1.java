package com.example.api.demo.author;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.author.dto.AuthorReq1;
import com.example.api.demo.author.dto.AuthorRes1;
import com.example.api.demo.generic.controllers.GenericController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/authors")
@Tag(name = "Author Controller", description = "Operations about Authors")
public class AuthorController1 extends GenericController<Author, AuthorReq1, AuthorRes1> {

    protected AuthorController1(AuthorService service, AuthorMapper mapper) {
        super(service, mapper, "v1");
    }

}
