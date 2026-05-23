# This project is for learning. These steps are for setting up the project.

1. Install Redis
2. Open a new terminal and instantiate a Redis server: $redis-server
3. Install Vault
4. Open a new terminal and instantiate a Vault server: $vault server -dev -dev-root-token-id="your-dev-token"
5. Open a new terminal at root of the project. Set environment variable and run: $VAULT_TOKEN=your-dev-token mvn spring-boot:run

# Populating the project with test data

The DataLoader.java file seeds the project with books, puts user credentials in Vault and then reads from the vault to create a user that can loggin.

# Security

When logged in you have the role:"USER" who can use GET and POST. DELETE and UPDATE are forbidden for this role.

# Fort manually trying the endpoints in browser

http://localhost:8080/swagger-ui.html
To login, use the following credentials:
user: user
password: pastaword

Book v2 has a pageable GET endpoint API.

Benchmarks:
When redis is used for cache, the performance is not as good as with in-memory. No notable difference in performance when using H2.

Tests were done with: $ab -n 1000000 -c 100 http://localhost:8080/api/v1/books

--- with no cache:
Server Software:  
Server Hostname: localhost
Server Port: 8080

Document Path: /api/v1/books
Document Length: 0 bytes

Concurrency Level: 100
Time taken for tests: 34.922 seconds
Complete requests: 1000000
Failed requests: 0
Non-2xx responses: 1000000
Total transferred: 465000000 bytes
HTML transferred: 0 bytes
Requests per second: 28634.98 [#/sec] (mean)
Time per request: 3.492 [ms] (mean)
Time per request: 0.035 [ms] (mean, across all concurrent requests)
Transfer rate: 13003.19 [Kbytes/sec] received

Connection Times (ms)
min mean[+/-sd] median max
Connect: 0 1 2.6 1 111
Processing: 0 2 6.7 1 330
Waiting: 0 2 3.5 1 111
Total: 0 3 7.2 3 334

Percentage of the requests served within a certain time (ms)
50% 3
66% 3
75% 3
80% 3
90% 4
95% 5
98% 8
99% 14
100% 334 (longest request)

--- with redis cache:
Server Software:  
Server Hostname: localhost
Server Port: 8080

Document Path: /api/v1/books
Document Length: 0 bytes

Concurrency Level: 100
Time taken for tests: 39.262 seconds
Complete requests: 1000000
Failed requests: 0
Non-2xx responses: 1000000
Total transferred: 465000000 bytes
HTML transferred: 0 bytes
Requests per second: 25469.64 [#/sec] (mean)
Time per request: 3.926 [ms] (mean)
Time per request: 0.039 [ms] (mean, across all concurrent requests)
Transfer rate: 11565.80 [Kbytes/sec] received

Connection Times (ms)
min mean[+/-sd] median max
Connect: 0 1 1.8 1 128
Processing: 0 3 6.0 2 376
Waiting: 0 3 3.9 2 149
Total: 0 4 6.2 3 377

Percentage of the requests served within a certain time (ms)
50% 3
66% 4
75% 4
80% 4
90% 5
95% 6
98% 10
99% 14
100% 377 (longest request)
