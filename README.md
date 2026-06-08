## Overview

This is a learning project. Follow the steps below to set up your development environment.

---

## Initial Setup

### 1. Start Redis and Vault

Set up the required infrastructure services by running these commands in separate terminals:

**Terminal 1 – Redis:**
```bash
redis-server
```

**Terminal 2 – Vault:**
```bash
vault server -dev -dev-root-token-id="your-dev-token"
```

### 2. Start the Application

**Terminal 3 – At project root:**
```bash
VAULT_TOKEN=your-dev-token mvn spring-boot:run
```

---

## Data Population

The **DataLoader.java** file automatically seeds the project with test data on startup. It handles:
- Creating sample books
- Storing user credentials in Vault
- Creating a login-enabled user account

---

## Testing the API

### Access Swagger UI
```
http://localhost:8080/swagger-ui.html
```

### Login Credentials
- **Username:** user
- **Password:** pastaword

### Key Features
- **User Role:** All authenticated users have the `USER` role and can access all endpoints
- **Pagination:** The v2 Books endpoint supports pageable GET requests
- **CSRF:** Disabled in SecurityConfig.java to allow curl requests during testing

---

## Performance Benchmarks

Benchmarks were conducted using **Apache Bench** with the following command:
```bash
ab -n 1000000 -c 100 http://localhost:8080/api/v1/books
```

| Metric | No Cache | Redis Cache |
|--------|----------|-------------|
| **Requests/sec** | 28,634.98 | 25,469.64 |
| **Mean response time** | 3.492 ms | 3.926 ms |
| **Total time** | 34.922 s | 39.262 s |
| **95th percentile** | 5 ms | 6 ms |
| **99th percentile** | 14 ms | 14 ms |

### Key Findings

**No caching performs better than Redis caching** for this workload. In-memory caching showed no notable performance difference compared to H2 database caching. Redis introduces network overhead that outweighs the caching benefits in this scenario.

---

## Detailed Benchmark Results

### No Cache Configuration

```
Concurrency: 100
Complete requests: 1,000,000
Failed requests: 0
Time taken: 34.922 seconds
Requests per second: 28,634.98

Response time distribution (ms):
  50%: 3 ms
  95%: 5 ms
  99%: 14 ms
  Max: 334 ms
```

### Redis Cache Configuration

```
Concurrency: 100
Complete requests: 1,000,000
Failed requests: 0
Time taken: 39.262 seconds
Requests per second: 25,469.64

Response time distribution (ms):
  50%: 3 ms
  95%: 6 ms
  99%: 14 ms
  Max: 377 ms
```
