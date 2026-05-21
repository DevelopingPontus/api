
1. Insatll Vault
2. Instantiate a Vault server with this CLI
$vault server -dev -dev-root-token-id="my-dev-root-token"
(This is only used like this for lerning, avoid in production)
3. Install Redis
4. Instantiate a Redis server with this CLI
$redis-server

Book v2 has a pageable API. Enter page number 0 or 1. There are 2 books per page. There are 3 books in totall.

http://localhost:8080/swagger-ui.html
To login, use the following credentials:
user: user
password: pastaword

Benchmarks:
When redis is used for cache, the performance is not as good as with in-memory. No notable difference in performance when using H2.

Tests were done with CLI
$ab -n 1000000 -c 100 http://localhost:8080/api/v1/books 

--- with no cache:
Server Software:        
Server Hostname:        localhost
Server Port:            8080

Document Path:          /api/v1/books
Document Length:        0 bytes

Concurrency Level:      100
Time taken for tests:   34.922 seconds
Complete requests:      1000000
Failed requests:        0
Non-2xx responses:      1000000
Total transferred:      465000000 bytes
HTML transferred:       0 bytes
Requests per second:    28634.98 [#/sec] (mean)
Time per request:       3.492 [ms] (mean)
Time per request:       0.035 [ms] (mean, across all concurrent requests)
Transfer rate:          13003.19 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   2.6      1     111
Processing:     0    2   6.7      1     330
Waiting:        0    2   3.5      1     111
Total:          0    3   7.2      3     334

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      3
  75%      3
  80%      3
  90%      4
  95%      5
  98%      8
  99%     14
 100%    334 (longest request)


--- with default cache:
Server Software:        
Server Hostname:        localhost
Server Port:            8080

Document Path:          /api/v1/books
Document Length:        0 bytes

Concurrency Level:      100
Time taken for tests:   35.049 seconds
Complete requests:      1000000
Failed requests:        0
Non-2xx responses:      1000000
Total transferred:      465000000 bytes
HTML transferred:       0 bytes
Requests per second:    28531.23 [#/sec] (mean)
Time per request:       3.505 [ms] (mean)
Time per request:       0.035 [ms] (mean, across all concurrent requests)
Transfer rate:          12956.08 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   2.0      1      98
Processing:     0    2  10.4      1     389
Waiting:        0    2   2.9      1      99
Total:          0    3  10.6      3     391

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      3
  75%      3
  80%      3
  90%      4
  95%      4
  98%      7
  99%     12
 100%    391 (longest request)


--- with redis cache:
Server Software:        
Server Hostname:        localhost
Server Port:            8080

Document Path:          /api/v1/books
Document Length:        0 bytes

Concurrency Level:      100
Time taken for tests:   51.638 seconds
Complete requests:      1000000
Failed requests:        0
Non-2xx responses:      1000000
Total transferred:      465000000 bytes
HTML transferred:       0 bytes
Requests per second:    19365.57 [#/sec] (mean)
Time per request:       5.164 [ms] (mean)
Time per request:       0.052 [ms] (mean, across all concurrent requests)
Transfer rate:          8793.94 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   3.0      1     169
Processing:     0    4  37.2      2    1673
Waiting:        0    3  37.1      2    1672
Total:          1    5  37.3      3    1675

Percentage of the requests served within a certain time (ms)
  50%      3
  66%      4
  75%      4
  80%      4
  90%      5
  95%      6
  98%      8
  99%     16
 100%   1675 (longest request)