package com.app.GovernmentSchemes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for StateUrlData model class.
 */
public class StateUrlDataTest {
    
    @Test
    public void testDefaultConstructor() {
        StateUrlData data = new StateUrlData();
        assertNull(data.getCode());
        assertNull(data.getName());
        assertNull(data.getUrl());
    }
    
    @Test
    public void testParameterizedConstructor() {
        StateUrlData data = new StateUrlData("CA", "California", "https://example.com/ca");
        assertEquals("CA", data.getCode());
        assertEquals("California", data.getName());
        assertEquals("https://example.com/ca", data.getUrl());
    }
    
    @Test
    public void testSetters() {
        StateUrlData data = new StateUrlData();
        
        data.setCode("NY");
        assertEquals("NY", data.getCode());
        
        data.setName("New York");
        assertEquals("New York", data.getName());
        
        data.setUrl("https://example.com/ny");
        assertEquals("https://example.com/ny", data.getUrl());
    }
    
    @Test
    public void testNullValues() {
        StateUrlData data = new StateUrlData(null, null, null);
        assertNull(data.getCode());
        assertNull(data.getName());
        assertNull(data.getUrl());
    }
}
