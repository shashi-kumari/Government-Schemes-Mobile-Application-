package com.app.GovernmentSchemes;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for PasswordUtils
 */
public class PasswordUtilsTest {
    
    @Test
    public void testPasswordEncryptionAndVerification() {
        String originalPassword = "testPassword123";
        
        // Encrypt the password
        String encryptedPassword = PasswordUtils.encryptPassword(originalPassword);
        
        // Verify the encrypted password is not null and not equal to original
        assertNotNull(encryptedPassword);
        assertNotEquals(originalPassword, encryptedPassword);
        assertTrue(encryptedPassword.contains(":"));
        
        // Verify the password can be validated
        assertTrue(PasswordUtils.verifyPassword(originalPassword, encryptedPassword));
        
        // Verify wrong password fails validation
        assertFalse(PasswordUtils.verifyPassword("wrongPassword", encryptedPassword));
    }
    
    @Test
    public void testDifferentPasswordsProduceDifferentHashes() {
        String password1 = "password1";
        String password2 = "password2";
        
        String encrypted1 = PasswordUtils.encryptPassword(password1);
        String encrypted2 = PasswordUtils.encryptPassword(password2);
        
        assertNotEquals(encrypted1, encrypted2);
    }
    
    @Test
    public void testSamePasswordProducesDifferentHashes() {
        String password = "samePassword";
        
        String encrypted1 = PasswordUtils.encryptPassword(password);
        String encrypted2 = PasswordUtils.encryptPassword(password);
        
        // Due to salt, same password should produce different hashes
        assertNotEquals(encrypted1, encrypted2);
        
        // But both should verify correctly
        assertTrue(PasswordUtils.verifyPassword(password, encrypted1));
        assertTrue(PasswordUtils.verifyPassword(password, encrypted2));
    }
}