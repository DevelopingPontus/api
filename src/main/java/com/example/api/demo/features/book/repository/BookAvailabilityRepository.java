package com.example.api.demo.features.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.demo.features.book.entity.BookAvailability;

@Repository
public interface BookAvailabilityRepository extends JpaRepository<BookAvailability, Long> {
    BookAvailability findByBookId(Long bookId);
}
