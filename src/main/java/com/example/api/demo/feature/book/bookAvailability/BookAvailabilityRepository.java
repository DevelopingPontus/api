package com.example.api.demo.feature.book.bookAvailability;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookAvailabilityRepository extends JpaRepository<BookAvailability, Long> {
    BookAvailability findByBookId(Long bookId);
}
