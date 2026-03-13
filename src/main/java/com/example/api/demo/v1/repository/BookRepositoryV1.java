package com.example.api.demo.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.demo.v1.entity.BookV2;

public interface BookRepositoryV1 extends JpaRepository<BookV2, Long> {

}
