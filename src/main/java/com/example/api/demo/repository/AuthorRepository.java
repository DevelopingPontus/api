package com.example.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.demo.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
