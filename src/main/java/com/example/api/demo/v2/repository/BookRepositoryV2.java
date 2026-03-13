package com.example.api.demo.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.demo.v2.entity.BookV2;

public interface BookRepositoryV2 extends JpaRepository<BookV2, Long> {

}
