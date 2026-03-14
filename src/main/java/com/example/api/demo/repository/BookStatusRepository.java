package com.example.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.demo.entity.BookStatus;

public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {

}
