package com.example.api.demo.feature.author;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Optional<Author> findByName(String name);

}
