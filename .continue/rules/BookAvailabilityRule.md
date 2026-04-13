# Book Availability Rule

## Overview
When attempting to loan a book, the system validates that the book is available. If a book is not available, a 400 Bad Request status code is returned.

## Implementation

### LoanService
The `LoanService.save()` method throws `BookAvailabilityException` when:
- A book with the specified `bookId` is found
- The book's `isAvailable()` method returns `false`

### BookAvailabilityException
Custom exception class with `@ResponseStatus(HttpStatus.BAD_REQUEST)` annotation to automatically return 400 status code.

### GlobalExceptionHandler
Handles `BookAvailabilityException` and returns a 400 Bad Request response with a descriptive message.

## API Behavior

**Endpoint**: `POST /api/v1/loans` or `POST /api/v1/loans/{id}`

**Request**: Loan creation or update

**Response when book unavailable**:
```json
{
  "status": "error",
  "code": 400,
  "message": "Book is not available for loan: Book with id X is not available"
}
```

## Testing

Example test case:
```java
@Test
void testLoanUnavailableBook() {
    // Create a book with availability = false
    Book book = createBookWithAvailability(false);
    
    // Try to loan the book
    LoanReq1 loanReq = new LoanReq1(1, book.getId());
    
    // Should throw BookAvailabilityException
    assertThatThrownBy(() -> loanService.save(List.of(loanReq)))
        .isInstanceOf(BookAvailabilityException.class)
        .hasMessageContaining("not available");
}
```

## Related Files
- `src/main/java/com/example/api/demo/features/loan/service/LoanService.java`
- `src/main/java/com/example/api/demo/common/exception/BookAvailabilityException.java`
- `src/main/java/com/example/api/demo/common/exception/GlobalExceptionHandler.java`