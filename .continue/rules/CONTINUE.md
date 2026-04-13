# Project Guide - Demo API

## 1. Project Overview

**Demo API** is a Spring Boot application built for demonstrating a loan and book management system. It's designed as a demo project following Spring Boot 4 best practices.

### Key Technologies
- **Java 25** (JDK 25)
- **Spring Boot 4.0.3**
- **Spring Data JPA** for database persistence
- **Spring Web MVC** for REST APIs
- **H2 Database** (in-memory) for development/testing
- **SpringDoc** for API documentation (Swagger UI)
- **Hibernate** for ORM
- **Lombok** for reducing boilerplate code
- **Validation** for input validation
- **Caching** with split caching strategy

### High-Level Architecture
```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   Controllers   │────▶│   Services      │────▶│    Repositories │
│  (Controllers)  │     │   (Services)    │     │    (Repositories)│
└─────────────────┘     └─────────────────┘     └─────────────────┘
                                 │
                                 ▼
                         ┌─────────────────┐
                         │   Caching       │
                         │   (Configured)  │
                         └─────────────────┘
                                 │
                                 ▼
                         ┌─────────────────┐
                         │    H2 Database  │
                         │   (In-memory)   │
                         └─────────────────┘
```

### Features
- **Loan Management** - CRUD operations for loans
- **Book Management** - CRUD operations for books and book availability
- **Author Management** - CRUD operations for authors
- **Generic Repository Pattern** - Abstract interface for reusable repository pattern
- **Generic Service Pattern** - Abstract interface for reusable service pattern
- **Generic Controller Pattern** - Abstract controller base class
- **Swagger/OpenAPI** - API documentation
- **Caching** - Split caching strategy (5 min for book availability, 1 hour for book metadata)
- **Validation** - Input validation using Spring Validation
- **Test Support** - Integration tests with H2 in-memory database

---

## 2. Getting Started

### Prerequisites
- **Java 25** (JDK 25)
- **Maven 3.8+** (or use `mvnw` wrapper)
- **IDE**: IntelliJ IDEA or Eclipse with Spring Boot plugins
- **PostgreSQL** (optional, for production database)

### Installation

1. Clone the repository
2. Open the project in your IDE
3. Verify dependencies are downloaded:
   ```bash
   mvn clean
   ```

### Running the Application

**Development Mode (H2 Database):**
```bash
mvnw spring-boot:run
```

Or run directly from IDE. The application will start on `http://localhost:8080`

**Production Mode (PostgreSQL):**
1. Configure `application.properties` or `application.yml`:
   ```properties
   # Uncomment and configure your PostgreSQL connection
   # spring.datasource.url=jdbc:postgresql://localhost:5432/egg_api
   # spring.datasource.username=postgres
   # spring.datasource.password=postgres
   # spring.jpa.hibernate.ddl-auto=update
   ```

### Basic Usage Examples

**API Documentation:**
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs (JSON): `http://localhost:8080/api-docs`

**Loan API Examples:**
```bash
# Create a loan
curl -X POST http://localhost:8080/api/v1/loans \
  -H "Content-Type: application/json" \
  -d '{"amount": 1000, "principal": 500, "interest": 500}'

# Get all loans
curl http://localhost:8080/api/v1/loans

# Get loan by ID
curl http://localhost:8080/api/v1/loans/1
```

**Book API Examples:**
```bash
# Create a book
curl -X POST http://localhost:8080/api/v1/books \
  -H "Content-Type: application/json" \
  -d '{"title": "Book 1", "authorId": 1, "quantity": 10}'

# Get book availability
curl http://localhost:8080/api/v1/books/1/availability
```

**Author API Examples:**
```bash
# Create an author
curl -X POST http://localhost:8080/api/v1/authors \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@example.com"}'
```

### Running Tests

**Unit Tests:**
```bash
mvnw test
```

**Integration Tests:**
```bash
mvnw test -Dtest=IntegrationTest
```

**Specific Test Class:**
```bash
mvnw test -Dtest=LoanControllerIntegrationTest
```

**Test Coverage:**
```bash
mvnw verify
```

---

## 3. Project Structure

### Directory Overview
```
demo/
├── .mvn/                          # Maven wrapper configuration
├── .postman/                     # Postman API documentation
├── src/
│   ├── main/
│   │   ├── java/com/example/api/demo/
│   │   │   ├── DemoApplication.java          # Main application class
│   │   │   ├── common/                       # Common utilities and interfaces
│   │   │   │   ├── configuration/            # Spring configurations
│   │   │   │   ├── controllers/             # Generic controller base
│   │   │   │   ├── exception/               # Global exception handling
│   │   │   │   ├── interfaces/              # Generic interfaces
│   │   │   │   ├── services/                # Generic service base
│   │   │   │   ├── wrappers/                # Response wrappers
│   │   │   │   └── ApiInfo.java             # API information
│   │   │   └── features/                    # Feature modules
│   │   │       ├── author/                  # Author feature
│   │   │       ├── book/                    # Book feature
│   │   │       ├── loan/                    # Loan feature
│   │   │       └── common/                  # Common utilities
│   │   └── resources/                      # Application resources
│   │       ├── application.properties       # Main application config
│   │       ├── static/                      # Static files (HTML, CSS, JS)
│   │       ├── templates/                   # Thymeleaf templates
│   │       └── static/
│   └── test/
│       ├── java/com/example/api/demo/
│       │   ├── BaseIntegrationTest.java     # Base integration test class
│       │   └── features/                    # Feature-specific tests
│       └── resources/
│           └── application-test.properties  # Test-specific config
├── postman/                              # Postman API documentation
├── pom.xml                               # Maven configuration
└── README.md
```

### Key Files and Their Roles

**Application Entry Point:**
- `src/main/java/com/example/api/demo/DemoApplication.java` - Main Spring Boot application class

**Configuration:**
- `src/main/resources/application.properties` - Main application configuration
- `src/main/resources/static/` - Static files (HTML, CSS, JS)
- `src/main/resources/templates/` - Thymeleaf templates

**Feature Modules:**
- `src/main/java/com/example/api/demo/features/author/` - Author management
- `src/main/java/com/example/api/demo/features/book/` - Book management
- `src/main/java/com/example/api/demo/features/loan/` - Loan management

**Generic Patterns:**
- `GenericRepository` - Abstract repository interface
- `GenericService` - Abstract service interface
- `GenericController` - Abstract controller base class
- `GenericWrapperResponse` - Response wrapper class

**Common Utilities:**
- `GlobalExceptionHandler` - Global exception handling
- `CachingConfig` - Cache configuration
- `CorsConfig` - CORS configuration
- `OpenApiConfig` - OpenAPI/Swagger configuration

### Configuration Files

**Maven (`pom.xml`):**
- Parent: Spring Boot 4.0.3
- Java version: 25
- Dependencies: Spring Boot starters, H2, Lombok, SpringDoc

**Application Properties:**
- Database: H2 in-memory (development)
- PostgreSQL (production, commented out)
- Swagger UI enabled
- H2 console enabled

---

## 4. Development Workflow

### Coding Standards

**Naming Conventions:**
- **Entities**: PascalCase (e.g., `Loan`, `Book`, `Author`)
- **DTOs**: PascalCase with `_Req` suffix for requests, `_Res` suffix for responses (e.g., `LoanReq1`, `LoanRes1`)
- **Services**: PascalCase (e.g., `LoanService`, `BookService`)
- **Repositories**: PascalCase (e.g., `LoanRepository`, `BookRepository`)
- **Controllers**: PascalCase (e.g., `LoanController`, `BookController`)
- **Packages**: Use feature-based structure (e.g., `features/loan/`, `features/book/`)

**Layer Organization:**
```
Entity Layer: `entity/` - JPA entities
Repository Layer: `repository/` - Data repositories
Mapper Layer: `mapper/` - Mapping interfaces
DTO Layer: `dto/` - Request/Response DTOs
Service Layer: `service/` - Business logic
Controller Layer: `controller/` - REST endpoints
```

### Testing Approach

**Integration Tests:**
- Extend `BaseIntegrationTest` for test setup
- Use H2 in-memory database for tests
- RESTTemplate for HTTP calls
- Test controllers directly

**Unit Tests:**
- Test service methods directly
- Mock repositories and services where appropriate

**Test Database:**
- Use `application-test.properties` for test configuration
- H2 database with `create-drop` strategy

**Test Naming:**
- Feature-specific tests: `BookControllerIntegrationTest`, `LoanServiceTest`
- Base tests: `DemoApplicationTests`, `BaseIntegrationTest`

### Build and Deployment

**Building:**
```bash
# Clean and build
mvnw clean package

# Run tests
mvnw test

# Run with Maven wrapper
mvnw spring-boot:run
```

**Deployment:**
- The application uses Maven packaging strategy
- Spring Boot Maven Plugin handles the build
- H2 database is suitable for development
- For production, uncomment PostgreSQL configuration

### Contribution Guidelines

1. **Git Workflow:**
   - Create a feature branch: `git checkout -b feature/your-feature`
   - Commit with conventional commit messages
   - Push and create PR

2. **Code Quality:**
   - Follow existing patterns and conventions
   - Add tests for new features
   - Update documentation as needed

3. **Documentation:**
   - Update `README.md` if changing public APIs
   - Update API documentation in Swagger

---

## 5. Key Concepts

### Domain-Specific Terminology

- **Entity**: JPA entity representing a database table (e.g., `Loan`, `Book`, `Author`)
- **DTO**: Data Transfer Object for request/response (e.g., `LoanReq1`, `LoanRes1`)
- **Mapper**: Interface for mapping entities to DTOs
- **Repository**: Interface extending Spring Data JPA repositories
- **Service**: Business logic layer
- **Controller**: REST controller handling HTTP requests

### Core Abstractions

**Generic Repository Pattern:**
```java
public interface GenericRepository<T, ID> {
    ID[] findAll();
    ID[] findByIds(ID[] ids);
    T findOne(ID id);
    T save(T entity);
    T update(T entity);
    void delete(ID id);
}
```

**Generic Service Pattern:**
```java
public interface GenericService<T, ID> {
    T findOne(ID id);
    T save(T entity);
    T update(T entity);
    void delete(ID id);
    T[] findAll();
}
```

**Generic Controller Pattern:**
```java
public abstract class GenericController<T, ID, TReq, TRes> {
    // Abstract methods for CRUD operations
}
```

### Design Patterns Used

1. **Repository Pattern** - Abstract data access layer
2. **Service Layer Pattern** - Business logic abstraction
3. **Generic Wrapper Pattern** - Reusable controller base
4. **Split Caching** - Different TTLs for different data types
5. **MVC Pattern** - Controller-Service-Repository separation

### Caching Strategy

**Split Caching:**
- **bookAvailability**: 5 minutes TTL (frequently updated data)
- **book**: 1 hour TTL (stable metadata)

This is configured in `CachingConfig.java` and uses `ConcurrentMapCacheManager`.

---

## 6. Common Tasks

### Adding a New Feature

1. Create a new feature directory: `features/your-feature/`
2. Create entity, repository, mapper, service, controller
3. Add configuration if needed
4. Add tests
5. Update API documentation

### Adding a New Endpoint

1. Create a controller extending `GenericController`
2. Add `@Autowired` dependency to service
3. Define REST endpoints with `@RequestMapping`
4. Add DTOs if needed
5. Add tests

### Configuring a Database

1. Edit `application.properties`
2. Uncomment PostgreSQL configuration
3. Update credentials
4. Set `spring.jpa.hibernate.ddl-auto=update` for auto-creation
5. Restart application

### Adding Caching

1. Extend `CachingConfig` or add new cache bean
2. Use `@Cacheable` annotation on service methods
3. Configure cache manager if needed

### Writing Integration Tests

1. Extend `BaseIntegrationTest`
2. Override test-specific methods
3. Use `@LocalServerPort` for port access
4. Use RESTTemplate for HTTP calls
5. Configure database in `application-test.properties`

---

## 7. Troubleshooting

### Common Issues

**Application won't start:**
- Check port is available (default is 8080)
- Verify database configuration
- Check console output for errors

**Database connection errors:**
- Verify database is running (H2 should be in-memory)
- Check credentials in `application.properties`
- For PostgreSQL, ensure service is running

**Swagger UI not loading:**
- Check `springdoc.swagger-ui.enabled=true`
- Verify port is correct
- Try accessing `/swagger-ui.html` directly

**Test failures:**
- Check test database configuration
- Ensure RESTTemplate is initialized
- Verify base URL is correct

### Debugging Tips

1. **Enable SQL Logging:**
   ```properties
   spring.jpa.show-sql=true
   ```

2. **Enable H2 Console:**
   ```properties
   spring.h2.console.enabled=true
   ```

3. **Check Stack Trace:**
   - Run with full stack trace in console
   - Use `--debug` flag if supported

4. **Verify Configuration:**
   ```bash
   mvnw spring-boot:run -Dspring.profiles.active=test
   ```

---

## 8. References

### Documentation Links

- **Spring Boot**: https://docs.spring.io/spring-boot/reference/
- **Spring Data JPA**: https://docs.spring.io/spring-data/jpa/
- **Spring Web MVC**: https://docs.spring.io/spring-framework/docs/current/reference/html/web-mvc.html
- **H2 Database**: https://h2database.com/html/
- **Swagger/OpenAPI**: https://springdoc.org/

### Project Resources

- **Postman Collections**: `postman/` directory
- **Environment Variables**: `postman/environments/`
- **API Specs**: `postman/specs/`
- **Globals**: `postman/globals/workspace.globals.yaml`

### Code Examples

- **Loan Feature**: `src/main/java/com/example/api/demo/features/loan/`
- **Book Feature**: `src/main/java/com/example/api/demo/features/book/`
- **Author Feature**: `src/main/java/com/example/api/demo/features/author/`

### Test Resources

- **Base Integration Test**: `src/test/java/com/example/api/demo/BaseIntegrationTest.java`
- **Test Configuration**: `src/test/resources/application-test.properties`

---

*This guide was generated for the Demo API project. For specific questions or additional details, refer to the respective feature modules.*