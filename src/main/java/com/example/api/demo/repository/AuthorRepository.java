package com.example.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.demo.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
