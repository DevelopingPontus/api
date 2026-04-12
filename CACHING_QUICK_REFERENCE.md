# Split Caching Implementation - Quick Reference

## What Was Changed

### New Files Created

1. **BookAvailability.java** - Entity for tracking book availability separately
   - Location: `features/book/entity/BookAvailability.java`
   - Contains: id, book (FK), available (boolean), lastUpdated (timestamp)

2. **BookAvailabilityRepository.java** - Data access for availability
   - Location: `features/book/repository/BookAvailabilityRepository.java`
   - Methods: `findByBookId(Long bookId)`

3. **BookAvailabilityService.java** - Business logic with caching
   - Location: `features/book/service/BookAvailabilityService.java`
   - Key methods:
     - `@Cacheable getAvailability(Long bookId)` - Reads from cache (5 min TTL)
     - `@CacheEvict updateAvailability(BookAvailability)` - Updates & refreshes cache
     - `@CacheEvict updateAvailabilityStatus(Long bookId, boolean available)` - Quick status update

4. **CachingConfig.java** - Spring Cache configuration
   - Location: `common/configuration/CachingConfig.java`
   - Enables `@EnableCaching`
   - Registers cache manager with "bookAvailability" cache

5. **CACHING_STRATEGY.md** - Full documentation
   - Comprehensive guide on architecture, usage, and future enhancements

### Modified Files

1. **Book.java** - Removed `available` boolean field
   - Added: One-to-one relationship with BookAvailability
   - Updated: `isAvailable()` delegates to availability entity
   - Updated: `setAvailable()` creates/updates BookAvailability

2. **BookReq1.java** (DTO) - Added availability field
   - New parameter: `boolean available`
   - Now: `BookReq1(String title, String author, String isbn, int publishedYear, boolean available)`

3. **BookService.java** - Integrated BookAvailabilityService
   - Injected: BookAvailabilityService dependency
   - Updated: `save()` method creates BookAvailability records
   - Added: `updateBookAvailability(Long bookId, boolean available)` method

4. **BookController1.java** - New endpoint for availability updates
   - New: `PUT /api/v1/books/{id}/availability?available=true/false`
   - Updates availability with independent cache refresh

5. **pom.xml** - Added Spring Cache dependency
   - New: `spring-boot-starter-cache`

6. **DataLoader.java** - Updated seed data
   - Added availability parameter to BookReq1 constructors

## How It Works

### Database Schema Change

```
BEFORE:
Book Table
├── id
├── title
├── isbn
├── published_year
├── available (boolean)  ← Updates invalidate whole book cache

AFTER:
Book Table                          BookAvailability Table
├── id                              ├── id
├── title                           ├── book_id (FK)
├── isbn                            ├── available (boolean)
├── published_year                  └── last_updated (timestamp)
└── (no availability)               ← Independent short-lived cache (5 min)
```

### Cache Flow

#### Reading Book with Availability

```
GET /api/v1/books/1
↓
BookService.findById(1)
↓
Book entity loaded from DB
↓
book.isAvailable()
  ↓
  BookAvailabilityService.getAvailability(1)  [First check cache]
    ↓
    Cache hit → Return cached BookAvailability (fast)
    OR
    Cache miss → Query BookAvailability from DB → Cache it
```

#### Updating Availability

```
PUT /api/v1/books/1/availability?available=false
↓
BookController1.updateAvailability(1, false)
↓
BookService.updateBookAvailability(1, false)
↓
BookAvailabilityService.updateAvailabilityStatus(1, false)
  ↓
  @CacheEvict triggers → Remove from cache
  ↓
  Save to database
  ↓
  Cache miss on next read → Data refreshed
```

## Performance Comparison

### Before Split Caching

- **Scenario**: Update book availability
- **Action**: Update Book entity → Full cache invalidation
- **Impact**: Next book read must query full Book + Author + related data
- **Time**: Higher latency for next reader

### After Split Caching

- **Scenario**: Update book availability
- **Action**: Update BookAvailability entity → Only availability cache invalidated
- **Impact**: Next book read uses cached Book metadata + fresh availability
- **Time**: Much faster, especially with many readers

## API Usage

### Create Book

```bash
curl -X POST http://localhost:8080/api/v1/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Boot Mastery",
    "author": "Spring Team",
    "isbn": "978-1234567890",
    "publishedYear": 2024,
    "available": true
  }'
```

### Get Book (with availability)

```bash
curl http://localhost:8080/api/v1/books/1
# Response includes: id, title, author, isbn, publishedYear, available
```

### Update Availability (New!)

```bash
# Book is now unavailable (loaned out)
curl -X PUT http://localhost:8080/api/v1/books/1/availability?available=false

# Book is back in stock
curl -X PUT http://localhost:8080/api/v1/books/1/availability?available=true
```

### Full Book Update

```bash
curl -X PUT http://localhost:8080/api/v1/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Boot Mastery 2nd Edition",
    "author": "Spring Team",
    "isbn": "978-1234567890",
    "publishedYear": 2025,
    "available": true
  }'
```

## Cache Monitoring

### Current Implementation

- In-memory cache (ConcurrentMapCacheManager)
- Good for: Single-instance development/testing
- TTL: 5 minutes for bookAvailability

### Add Logging (Optional)

```java
// In CachingConfig
@Bean
public CacheManager cacheManager() {
    ConcurrentMapCacheManager cacheManager =
        new ConcurrentMapCacheManager("bookAvailability");

    // Optional: Add logging
    cacheManager.setAllowNullValues(true);

    return cacheManager;
}
```

### Monitor Cache Hits/Misses

```java
// In BookAvailabilityService
private final CacheManager cacheManager;

public CacheStats getStats() {
    Cache cache = cacheManager.getCache("bookAvailability");
    // Implement stats retrieval based on cache provider
}
```

## Next Steps

### 1. Scale to Multiple Instances (Add Redis)

```xml
<!-- Add to pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

### 2. Implement Expiration-based TTL

- Current: Manual cache eviction on updates
- Future: Automatic expiration for stale data

### 3. Add Cache Metrics

- Use Micrometer for monitoring
- Track hit rates, eviction rates
- Alert on cache performance

### 4. Extend to Other Entities

- Apply same pattern to Author (less frequently updated)
- Create separate services for different update frequencies

## Troubleshooting

### Cache Not Working?

1. Check `@EnableCaching` is in CachingConfig ✓
2. Verify `@Cacheable` and `@CacheEvict` annotations are present ✓
3. Ensure cache name is consistent: "bookAvailability" ✓

### Stale Data After Update?

1. Verify `@CacheEvict` is called on update methods
2. Check that key generation is correct: `key = "#bookId"` or `key = "#availability.book.id"`
3. Monitor cache eviction logs

### All Requests Still Hit Database?

1. Add logging to BookAvailabilityService
2. Check if `@Cacheable` method is actually being called
3. Verify proxy creation is working (Spring needs proxies for annotations)

## Testing the Cache

```bash
# Terminal 1: Start application
mvn spring-boot:run

# Terminal 2: Run tests
# 1st request - Cache miss (see SQL in logs)
curl http://localhost:8080/api/v1/books/1

# 2nd request (within 5 min) - Cache hit (no SQL)
curl http://localhost:8080/api/v1/books/1

# Update availability - Cache invalidated
curl -X PUT http://localhost:8080/api/v1/books/1/availability?available=false

# 3rd request - Cache miss, fresh data
curl http://localhost:8080/api/v1/books/1
```

## Files Summary

```
src/main/java/com/example/api/demo/
├── features/book/
│   ├── entity/
│   │   ├── Book.java (modified - now uses BookAvailability)
│   │   └── BookAvailability.java (NEW - separate availability entity)
│   ├── repository/
│   │   ├── BookRepository.java (unchanged)
│   │   └── BookAvailabilityRepository.java (NEW - availability data access)
│   ├── service/
│   │   ├── BookService.java (modified - uses BookAvailabilityService)
│   │   └── BookAvailabilityService.java (NEW - caching logic)
│   ├── controller/
│   │   └── BookController1.java (modified - new availability endpoint)
│   └── dto/
│       └── BookReq1.java (modified - added available field)
├── common/configuration/
│   ├── CachingConfig.java (NEW - Spring Cache configuration)
│   └── ... other configs
└── DataLoader.java (modified - updated seed data)

Root:
├── pom.xml (modified - added spring-boot-starter-cache)
└── CACHING_STRATEGY.md (NEW - detailed documentation)
```

## Questions?

Refer to [CACHING_STRATEGY.md](./CACHING_STRATEGY.md) for:

- Architecture details
- Performance benefits
- Future enhancements
- Redis integration guide
- Multi-level caching strategy
