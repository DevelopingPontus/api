package com.example.api.demo.controller.v2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.demo.dto.v2.AuthorRequest2;
import com.example.api.demo.dto.v2.AuthorResponse2;
import com.example.api.demo.entity.Author;
import com.example.api.demo.service.GenericService;


@RestController
@RequestMapping("/api/v2/authors")
public class AuthorController extends AbstractGenericController<Author, Long, AuthorRequest2, AuthorResponse2> {

    public AuthorController(GenericService<Author, Long> service) {
        super(service, AuthorRequest2.class, AuthorResponse2.class);
    }

    @Override
    protected Author convertToEntity(AuthorRequest2 request) {
        // You may use a mapping framework (e.g., MapStruct) here.
        return new Author(request.firstName(), request.lastName());
    }

    @Override
    protected AuthorResponse2 convertToResponse(Author entity) {
        return new AuthorResponse2(entity.getId(), entity.getFirstName(), entity.getLastName());
    }
}
