package com.example.api.demo.features.book.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.api.demo.features.book.entity.BookAvailability;
import com.example.api.demo.features.book.repository.BookAvailabilityRepository;

/**
 * Service for managing book availability with short-lived caching.
 * Availability data is cached separately from book metadata,
 * allowing for more frequent updates without invalidating the book cache.
 *
 * Cache Configuration:
 * - bookAvailability: TTL = 5 minutes (short-lived for frequent updates)
 * - Used when book data might be cached for hours/days
 */
@Service
public class BookAvailabilityService {
    private final BookAvailabilityRepository bookAvailabilityRepository;

    public BookAvailabilityService(BookAvailabilityRepository bookAvailabilityRepository) {
        this.bookAvailabilityRepository = bookAvailabilityRepository;
    }

    /**
     * Get book availability with caching.
     * Cached for 5 minutes to allow frequent availability updates.
     */
    @Cacheable(value = "bookAvailability", key = "#bookId")
    public BookAvailability getAvailability(Long bookId) {
        return bookAvailabilityRepository.findByBookId(bookId);
    }

    /**
     * Update book availability and refresh cache.
     */
    @CacheEvict(value = "bookAvailability", key = "#availability.book.id")
    public BookAvailability updateAvailability(BookAvailability availability) {
        return bookAvailabilityRepository.save(availability);
    }

    /**
     * Update availability status for a specific book.
     * Refreshes the cache for that book.
     */
    @CacheEvict(value = "bookAvailability", key = "#bookId")
    public BookAvailability updateAvailabilityStatus(Long bookId, boolean available) {
        BookAvailability availability = bookAvailabilityRepository.findByBookId(bookId);
        if (availability != null) {
            availability.setAvailable(available);
            return bookAvailabilityRepository.save(availability);
        }
        return null;
    }

    /**
     * Delete availability tracking and refresh cache.
     */
    @CacheEvict(value = "bookAvailability", key = "#bookId")
    public void deleteAvailability(Long bookId) {
        BookAvailability availability = bookAvailabilityRepository.findByBookId(bookId);
        if (availability != null) {
            bookAvailabilityRepository.delete(availability);
        }
    }
}
