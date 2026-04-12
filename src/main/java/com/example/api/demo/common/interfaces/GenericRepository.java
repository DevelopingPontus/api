package com.example.api.demo.common.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Generic JPA Repository - Base interface for all entity types
 */
@NoRepositoryBean
public interface GenericRepository<T extends EntityInterface> extends JpaRepository<T, Long> {
}