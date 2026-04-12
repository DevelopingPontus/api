# Split Caching Strategy

## Overview

This project implements a **split caching strategy** to optimize performance by decoupling book metadata from availability data, allowing independent cache invalidation and refresh cycles.

## Architecture

### Problem Solved

- **Traditional Approach**: Cache entire Book entity → availability changes force full book cache invalidation
- **Split Caching**: Separate `Book` and `BookAvailability` entities → availability updates only refresh the short-lived cache

### Implementation

#### Entities

- **Book** (`features/book/entity/Book.java`)
  - Contains: title, author, ISBN, published year
  - Has ONE-TO-ONE relationship with BookAvailability
  - Methods: `isAvailable()`, `setAvailable()` delegate to BookAvailability

- **BookAvailability** (`features/book/entity/BookAvailability.java`)
  - Contains: bookId, available (boolean), lastUpdated (timestamp)
  - Lightweight, frequently updated
  - Tracked independently in database

#### Services

- **BookService** (`features/book/service/BookService.java`)
  - Handles book operations
  - Coordinates with BookAvailabilityService for availability updates
  - Method: `updateBookAvailability(Long bookId, boolean available)`

- **BookAvailabilityService** (`features/book/service/BookAvailabilityService.java`)
  - Manages availability caching
  - Methods:
    - `getAvailability(bookId)` - Cached (5 min TTL)
    - `updateAvailability(availability)` - Cache evict on update
    - `updateAvailabilityStatus(bookId, available)` - Cache evict on status change

#### Controllers

- **BookController1** (`features/book/controller/BookController1.java`)
  - New endpoint: `PUT /api/v1/books/{id}/availability?available=true/false`
  - Updates availability with automatic cache refresh

#### Configuration

- **CachingConfig** (`common/configuration/CachingConfig.java`)
  - Enables Spring caching with `@EnableCaching`
  - Defines cache managers
  - Can be extended for Redis or distributed caching

## Cache Configuration

### Current Setup (In-Memory)

```java
CacheManager: ConcurrentMapCacheManager
Caches:
  - bookAvailability: In-memory, 5 minute TTL
```

### Cache Annotations Used

```java
@Cacheable(value = "bookAvailability", key = "#bookId")
// Reads from cache if exists, executes method if miss

@CacheEvict(value = "bookAvailability", key = "#bookId")
// Removes entry from cache on update/delete
```

## Usage Examples

### Creating a Book with Availability

```json
POST /api/v1/books
{
  "title": "Spring in Action",
  "author": "Craig Walls",
  "isbn": "978-1-61729-857-4",
  "publishedYear": 2022,
  "available": true
}
```

### Updating Book Availability (Without Cache Invalidation)

```
PUT /api/v1/books/1/availability?available=false
```

- Only availability cache is refreshed (5 min TTL)
- Book metadata cache remains valid (can be set to longer TTL)

### Full Book Update (Invalidates All Caches)

```json
PUT /api/v1/books/1
{
  "title": "Spring Boot in Action",
  "author": "Craig Walls",
  "isbn": "978-1-61729-857-4",
  "publishedYear": 2023,
  "available": true
}
```

## Cache TTL Strategy

| Component        | TTL       | Reason                              |
| ---------------- | --------- | ----------------------------------- |
| bookAvailability | 5 minutes | Frequently changes (loans, returns) |
| book (metadata)  | 1 hour+   | Stable data (title, author, ISBN)   |

## Performance Benefits

1. **Reduced Cache Invalidation**: Availability updates don't invalidate book metadata
2. **Faster Updates**: Availability changes reflect in cache within 5 minutes
3. **Scalability**: Separating concerns allows for different caching strategies
4. **Database Load**: Fewer full entity updates and cache refreshes

## Future Enhancements

### 1. Redis Integration

```xml
<!-- Add to pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 2. Time-based Cache Expiration

```java
@Configuration
public class CachingConfig {
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        return RedisCacheManager.create(factory);
    }
}
```

### 3. Monitoring and Metrics

- Add cache hit/miss metrics
- Monitor cache eviction frequency
- Track availability update latency

### 4. Multi-level Caching

- L1: In-memory cache (fast, per-instance)
- L2: Redis cache (distributed, shared)
- L3: Database (source of truth)

## DTOs Update

### BookReq1 (now includes availability)

```java
public record BookReq1(
    String title,
    String author,
    String isbn,
    int publishedYear,
    boolean available  // NEW FIELD
)
```

### BookRes1 (unchanged)

```java
public record BookRes1(
    Long id,
    String title,
    String author,
    String isbn,
    int publishedYear,
    boolean available
)
```

## Testing

### Cache Behavior

```bash
# 1st request - Cache miss, query database
curl http://localhost:8080/api/v1/books/1

# 2nd request (within 5 min) - Cache hit
curl http://localhost:8080/api/v1/books/1

# Update availability - Cache evict
curl -X PUT http://localhost:8080/api/v1/books/1/availability?available=false

# 3rd request - Cache miss (data refreshed)
curl http://localhost:8080/api/v1/books/1
```

## Troubleshooting

### Cache Not Working

1. Ensure `@EnableCaching` is in CachingConfig
2. Check that methods use `@Cacheable` and `@CacheEvict` annotations
3. Verify cache name matches configuration

### Stale Data Issues

1. Reduce TTL in cache configuration
2. Add cache eviction on related entity updates
3. Implement manual cache clearing via admin endpoint

## References

- [Spring Caching Documentation](https://spring.io/guides/gs/caching/)
- [Redis Caching](https://spring.io/guides/gs/messaging-redis/)
- [Cache Aside Pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/cache-aside)
