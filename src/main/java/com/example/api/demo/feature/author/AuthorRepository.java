package com.example.api.demo.feature.author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Author findByName(String name);

}
