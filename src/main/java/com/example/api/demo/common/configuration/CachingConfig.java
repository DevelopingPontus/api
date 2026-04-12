package com.example.api.demo.common.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Caching configuration for split caching strategy.
 * 
 * Cache Strategy:
 * - bookAvailability: 5 minutes TTL (for frequently updated data)
 * - book: 1 hour TTL (for stable metadata - if needed later)
 */
@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("bookAvailability");
    }
}
