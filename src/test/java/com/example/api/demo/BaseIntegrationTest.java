/**package com.example.api.demo;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    protected RestTemplate restTemplate;

    protected String getBaseUrl(String endpoint) {
        return "http://localhost:" + port + "/api/v1/" + endpoint;
    }

    protected void initializeRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
    }

    protected String prettyPrint(Object object) {
        return object != null ? object.toString() : "null";
    }

    protected void assertResponseSuccess(int statusCode, String message) {
        if (statusCode < 200 || statusCode >= 300) {
            throw new AssertionError("Expected successful response, but got: " + statusCode + " - " + message);
        }
    }
}
*/