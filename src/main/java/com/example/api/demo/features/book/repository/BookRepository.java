package com.example.api.demo.features.book.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;

import com.example.api.demo.features.book.entity.Book;
import com.example.api.demo.common.interfaces.GenericRepository;

public interface BookRepository extends GenericRepository<Book> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.id = ?1")
    Optional<Book> findByIdWithLock(Long id);
}
