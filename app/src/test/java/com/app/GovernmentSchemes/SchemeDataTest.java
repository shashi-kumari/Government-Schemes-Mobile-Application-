package com.app.GovernmentSchemes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the SchemeData model class.
 */
public class SchemeDataTest {

    @Test
    public void testDefaultConstructor() {
        SchemeData schemeData = new SchemeData();
        assertNull(schemeData.getScheme());
        assertNull(schemeData.getDescription());
        assertNull(schemeData.getNotificationDate());
        assertNull(schemeData.getUrl());
        assertEquals(0, schemeData.getCreatedAt());
    }

    @Test
    public void testParameterizedConstructor() {
        long beforeCreation = System.currentTimeMillis();
        SchemeData schemeData = new SchemeData(
                "Test Scheme",
                "Test Description",
                "2024-01-01",
                "https://example.com"
        );
        long afterCreation = System.currentTimeMillis();
        
        assertEquals("Test Scheme", schemeData.getScheme());
        assertEquals("Test Description", schemeData.getDescription());
        assertEquals("2024-01-01", schemeData.getNotificationDate());
        assertEquals("https://example.com", schemeData.getUrl());
        // Verify createdAt is set automatically within the expected time range
        assertTrue(schemeData.getCreatedAt() >= beforeCreation);
        assertTrue(schemeData.getCreatedAt() <= afterCreation);
    }

    @Test
    public void testParameterizedConstructorWithCreatedAt() {
        long customCreatedAt = 1609459200000L; // Jan 1, 2021
        SchemeData schemeData = new SchemeData(
                "Test Scheme",
                "Test Description",
                "2024-01-01",
                "https://example.com",
                customCreatedAt
        );
        
        assertEquals("Test Scheme", schemeData.getScheme());
        assertEquals("Test Description", schemeData.getDescription());
        assertEquals("2024-01-01", schemeData.getNotificationDate());
        assertEquals("https://example.com", schemeData.getUrl());
        assertEquals(customCreatedAt, schemeData.getCreatedAt());
    }

    @Test
    public void testSetters() {
        SchemeData schemeData = new SchemeData();
        
        schemeData.setScheme("Agriculture Scheme");
        schemeData.setDescription("A scheme for farmers");
        schemeData.setNotificationDate("2024-06-15");
        schemeData.setUrl("https://agriculture.gov.in");
        schemeData.setCreatedAt(1609459200000L);
        
        assertEquals("Agriculture Scheme", schemeData.getScheme());
        assertEquals("A scheme for farmers", schemeData.getDescription());
        assertEquals("2024-06-15", schemeData.getNotificationDate());
        assertEquals("https://agriculture.gov.in", schemeData.getUrl());
        assertEquals(1609459200000L, schemeData.getCreatedAt());
    }

    @Test
    public void testIsRecentlyAdded_WithRecentTimestamp() {
        // Scheme created just now should be considered recently added
        SchemeData schemeData = new SchemeData(
                "Test Scheme",
                "Test Description",
                "2024-01-01",
                "https://example.com"
        );
        
        assertTrue(schemeData.isRecentlyAdded());
    }

    @Test
    public void testIsRecentlyAdded_WithOldTimestamp() {
        // Scheme created 25 hours ago should not be considered recently added
        long twentyFiveHoursAgo = System.currentTimeMillis() - (25 * 60 * 60 * 1000);
        SchemeData schemeData = new SchemeData(
                "Test Scheme",
                "Test Description",
                "2024-01-01",
                "https://example.com",
                twentyFiveHoursAgo
        );
        
        assertFalse(schemeData.isRecentlyAdded());
    }

    @Test
    public void testIsRecentlyAdded_WithZeroTimestamp() {
        // Scheme with zero timestamp (unset) should not be considered recently added
        SchemeData schemeData = new SchemeData();
        schemeData.setCreatedAt(0);
        
        assertFalse(schemeData.isRecentlyAdded());
    }

    @Test
    public void testIsRecentlyAdded_WithBoundaryTimestamp() {
        // Scheme created exactly 23 hours ago should still be considered recently added
        long twentyThreeHoursAgo = System.currentTimeMillis() - (23 * 60 * 60 * 1000);
        SchemeData schemeData = new SchemeData(
                "Test Scheme",
                "Test Description",
                "2024-01-01",
                "https://example.com",
                twentyThreeHoursAgo
        );
        
        assertTrue(schemeData.isRecentlyAdded());
    }

    @Test
    public void testHasValidUrl_WithHttpsUrl() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("https://example.com");
        
        assertTrue(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithHttpUrl() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("http://example.com");
        
        assertTrue(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithNaUrl() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("na");
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithNaUpperCase() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("NA");
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithEmptyUrl() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("");
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithNullUrl() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl(null);
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithMixedCaseNa() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("Na");
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithJavascriptScheme() {
        // Security test: javascript URLs should not be considered valid
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("javascript:alert('xss')");
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithFileScheme() {
        // Security test: file URLs should not be considered valid
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("file:///etc/passwd");
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithDataScheme() {
        // Security test: data URLs should not be considered valid
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("data:text/html,<script>alert('xss')</script>");
        
        assertFalse(schemeData.hasValidUrl());
    }

    @Test
    public void testHasValidUrl_WithUpperCaseHttps() {
        // HTTPS in uppercase should be valid
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("HTTPS://example.com");
        
        assertTrue(schemeData.hasValidUrl());
    }

    @Test
    public void testSortSchemesByCreatedAtDescending() {
        // Create schemes with different createdAt timestamps
        long now = System.currentTimeMillis();
        SchemeData scheme1 = new SchemeData("Scheme 1", "Description 1", "2024-01-01", "https://example1.com", now - 3000); // oldest
        SchemeData scheme2 = new SchemeData("Scheme 2", "Description 2", "2024-01-02", "https://example2.com", now - 1000); // middle
        SchemeData scheme3 = new SchemeData("Scheme 3", "Description 3", "2024-01-03", "https://example3.com", now);        // newest

        // Add schemes in unsorted order
        List<SchemeData> schemes = new ArrayList<>();
        schemes.add(scheme1);
        schemes.add(scheme3);
        schemes.add(scheme2);

        // Sort by createdAt descending (latest first) - same logic as in SchemeDataProvider
        Collections.sort(schemes, new Comparator<SchemeData>() {
            @Override
            public int compare(SchemeData s1, SchemeData s2) {
                return Long.compare(s2.getCreatedAt(), s1.getCreatedAt());
            }
        });

        // Verify the order is newest first
        assertEquals("Scheme 3", schemes.get(0).getScheme()); // newest should be first
        assertEquals("Scheme 2", schemes.get(1).getScheme()); // middle should be second
        assertEquals("Scheme 1", schemes.get(2).getScheme()); // oldest should be last
    }

    @Test
    public void testSortSchemesByCreatedAtDescending_WithZeroTimestamps() {
        // Test that schemes with zero timestamps (legacy schemes) are sorted to the end
        long now = System.currentTimeMillis();
        SchemeData schemeWithTimestamp = new SchemeData("New Scheme", "Description", "2024-01-01", "https://example.com", now);
        SchemeData legacyScheme = new SchemeData("Legacy Scheme", "Description", "2024-01-02", "https://example.com", 0);

        List<SchemeData> schemes = new ArrayList<>();
        schemes.add(legacyScheme);
        schemes.add(schemeWithTimestamp);

        // Sort by createdAt descending
        Collections.sort(schemes, new Comparator<SchemeData>() {
            @Override
            public int compare(SchemeData s1, SchemeData s2) {
                return Long.compare(s2.getCreatedAt(), s1.getCreatedAt());
            }
        });

        // Scheme with timestamp should be first, legacy scheme (0 timestamp) should be last
        assertEquals("New Scheme", schemes.get(0).getScheme());
        assertEquals("Legacy Scheme", schemes.get(1).getScheme());
    }
}
