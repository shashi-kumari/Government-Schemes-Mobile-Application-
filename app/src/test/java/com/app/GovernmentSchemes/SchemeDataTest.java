package com.app.GovernmentSchemes;

import org.junit.Test;

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
    }

    @Test
    public void testParameterizedConstructor() {
        SchemeData schemeData = new SchemeData(
                "Test Scheme",
                "Test Description",
                "2024-01-01",
                "https://example.com"
        );
        
        assertEquals("Test Scheme", schemeData.getScheme());
        assertEquals("Test Description", schemeData.getDescription());
        assertEquals("2024-01-01", schemeData.getNotificationDate());
        assertEquals("https://example.com", schemeData.getUrl());
    }

    @Test
    public void testSetters() {
        SchemeData schemeData = new SchemeData();
        
        schemeData.setScheme("Agriculture Scheme");
        schemeData.setDescription("A scheme for farmers");
        schemeData.setNotificationDate("2024-06-15");
        schemeData.setUrl("https://agriculture.gov.in");
        
        assertEquals("Agriculture Scheme", schemeData.getScheme());
        assertEquals("A scheme for farmers", schemeData.getDescription());
        assertEquals("2024-06-15", schemeData.getNotificationDate());
        assertEquals("https://agriculture.gov.in", schemeData.getUrl());
    }

    @Test
    public void testHasValidUrl_WithValidUrl() {
        SchemeData schemeData = new SchemeData();
        schemeData.setUrl("https://example.com");
        
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
}
