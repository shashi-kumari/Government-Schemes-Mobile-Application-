package com.app.GovernmentSchemes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for AdminUrlManagementActivity
 */
public class AdminUrlManagementActivityTest {

    @Test
    public void stateUrlData_constructor_setsFieldsCorrectly() {
        AdminUrlManagementActivity.StateUrlData data = 
                new AdminUrlManagementActivity.StateUrlData("AP", "Andhra Pradesh", "https://example.com/ap");
        
        assertEquals("AP", data.code);
        assertEquals("Andhra Pradesh", data.name);
        assertEquals("https://example.com/ap", data.url);
    }

    @Test
    public void stateUrlData_defaultConstructor_createsEmptyObject() {
        AdminUrlManagementActivity.StateUrlData data = 
                new AdminUrlManagementActivity.StateUrlData();
        
        assertNull(data.code);
        assertNull(data.name);
        assertNull(data.url);
    }

    @Test
    public void schemeSector_hasAllCategories() {
        // Verify all expected categories exist
        assertEquals(6, SchemeSector.values().length);
        
        assertNotNull(SchemeSector.AGRICULTURE);
        assertNotNull(SchemeSector.BANKING);
        assertNotNull(SchemeSector.BUSINESS);
        assertNotNull(SchemeSector.EDUCATION);
        assertNotNull(SchemeSector.HEALTH);
        assertNotNull(SchemeSector.HOUSING);
    }

    @Test
    public void schemeSector_displayNames_areCorrect() {
        assertEquals("Agriculture", SchemeSector.AGRICULTURE.getDisplayName());
        assertEquals("Banking", SchemeSector.BANKING.getDisplayName());
        assertEquals("Business", SchemeSector.BUSINESS.getDisplayName());
        assertEquals("Education", SchemeSector.EDUCATION.getDisplayName());
        assertEquals("Health", SchemeSector.HEALTH.getDisplayName());
        assertEquals("Housing", SchemeSector.HOUSING.getDisplayName());
    }

}
