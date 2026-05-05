package com.example.api.demo.features.author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Author findByName(String name);

}
