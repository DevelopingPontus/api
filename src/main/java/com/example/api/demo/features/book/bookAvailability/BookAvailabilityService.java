package com.example.api.demo.features.book.bookAvailability;

import java.time.LocalDateTime;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookAvailabilityService {
    private final BookAvailabilityRepository bookAvailabilityRepository;

    public BookAvailabilityService(BookAvailabilityRepository bookAvailabilityRepository) {
        this.bookAvailabilityRepository = bookAvailabilityRepository;
    }

    @Cacheable(value = "bookAvailability", key = "#bookId")
    public BookAvailability getAvailability(Long bookId) {
        return bookAvailabilityRepository.findByBookId(bookId);
    }

    @CacheEvict(value = "bookAvailability", key = "#availability.book.id")
    public BookAvailability updateAvailability(BookAvailability availability) {
        return bookAvailabilityRepository.save(availability);
    }

    @CacheEvict(value = "bookAvailability", key = "#bookId")
    public BookAvailability updateAvailabilityStatus(Long bookId, boolean available) {
        BookAvailability availability = bookAvailabilityRepository.findByBookId(bookId);
        if (availability != null) {
            availability.setAvailable(available);
            availability.setLastUpdated(LocalDateTime.now());
            return bookAvailabilityRepository.save(availability);
        }
        return null;
    }

    @CacheEvict(value = "bookAvailability", key = "#bookId")
    public void deleteAvailability(Long bookId) {
        BookAvailability availability = bookAvailabilityRepository.findByBookId(bookId);
        if (availability != null) {
            bookAvailabilityRepository.delete(availability);
        }
    }
}
