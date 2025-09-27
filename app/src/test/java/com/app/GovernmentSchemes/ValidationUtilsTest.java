package com.app.GovernmentSchemes;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ValidationUtils
 */
public class ValidationUtilsTest {
    
    @Test
    public void testValidName() {
        assertTrue(ValidationUtils.isValidName("John Doe"));
        assertTrue(ValidationUtils.isValidName("Mary Jane"));
        assertTrue(ValidationUtils.isValidName("A.B. Johnson"));
        assertTrue(ValidationUtils.isValidName("O'Connor"));
        
        assertFalse(ValidationUtils.isValidName(""));
        assertFalse(ValidationUtils.isValidName("   "));
        assertFalse(ValidationUtils.isValidName("A"));
        assertFalse(ValidationUtils.isValidName("John123"));
        assertFalse(ValidationUtils.isValidName(null));
    }
    
    @Test
    public void testValidEmail() {
        assertTrue(ValidationUtils.isValidEmail("test@example.com"));
        assertTrue(ValidationUtils.isValidEmail("user.name@domain.co.uk"));
        assertTrue(ValidationUtils.isValidEmail("test123@gmail.com"));
        
        assertFalse(ValidationUtils.isValidEmail(""));
        assertFalse(ValidationUtils.isValidEmail("   "));
        assertFalse(ValidationUtils.isValidEmail("invalid-email"));
        assertFalse(ValidationUtils.isValidEmail("@domain.com"));
        assertFalse(ValidationUtils.isValidEmail("test@"));
        assertFalse(ValidationUtils.isValidEmail(null));
    }
    
    @Test
    public void testValidUsername() {
        assertTrue(ValidationUtils.isValidUsername("user123"));
        assertTrue(ValidationUtils.isValidUsername("john_doe"));
        assertTrue(ValidationUtils.isValidUsername("TestUser"));
        assertTrue(ValidationUtils.isValidUsername("abc"));
        
        assertFalse(ValidationUtils.isValidUsername(""));
        assertFalse(ValidationUtils.isValidUsername("   "));
        assertFalse(ValidationUtils.isValidUsername("ab")); // too short
        assertFalse(ValidationUtils.isValidUsername("user@name")); // invalid char
        assertFalse(ValidationUtils.isValidUsername("user-name")); // invalid char
        assertFalse(ValidationUtils.isValidUsername("a".repeat(21))); // too long
        assertFalse(ValidationUtils.isValidUsername(null));
    }
    
    @Test
    public void testValidPassword() {
        assertTrue(ValidationUtils.isValidPassword("password123"));
        assertTrue(ValidationUtils.isValidPassword("123456"));
        assertTrue(ValidationUtils.isValidPassword("abcdef"));
        
        assertFalse(ValidationUtils.isValidPassword(""));
        assertFalse(ValidationUtils.isValidPassword("12345")); // too short
        assertFalse(ValidationUtils.isValidPassword(null));
    }
    
    @Test
    public void testErrorMessages() {
        assertNotNull(ValidationUtils.getNameErrorMessage(""));
        assertNotNull(ValidationUtils.getEmailErrorMessage("invalid"));
        assertNotNull(ValidationUtils.getUsernameErrorMessage("ab"));
        assertNotNull(ValidationUtils.getPasswordErrorMessage("123"));
        
        assertNull(ValidationUtils.getNameErrorMessage("John Doe"));
        assertNull(ValidationUtils.getEmailErrorMessage("test@example.com"));
        assertNull(ValidationUtils.getUsernameErrorMessage("user123"));
        assertNull(ValidationUtils.getPasswordErrorMessage("password123"));
    }
}