package com.example.api.demo;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

/**
 * Base class for integration tests.
 * Provides common setup, utilities, and helper methods for all integration
 * tests.
 */
public abstract class BaseIntegrationTest {

    @LocalServerPort
    protected int port;

    protected RestTemplate restTemplate;

    /**
     * Get the base URL for API endpoints
     */
    protected String getBaseUrl(String endpoint) {
        return "http://localhost:" + port + "/api/v1/" + endpoint;
    }

    /**
     * Initialize RestTemplate if not already initialized
     */
    protected void initializeRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
    }

    /**
     * Pretty print response for debugging
     */
    protected String prettyPrint(Object object) {
        return object != null ? object.toString() : "null";
    }

    /**
     * Assert that a response is successful
     */
    protected void assertResponseSuccess(int statusCode, String message) {
        if (statusCode < 200 || statusCode >= 300) {
            throw new AssertionError("Expected successful response, but got: " + statusCode + " - " + message);
        }
    }
}
