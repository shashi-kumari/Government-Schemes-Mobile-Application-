package com.app.GovernmentSchemes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password encryption and validation
 */
public class PasswordUtils {
    
    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    
    /**
     * Generate a random salt
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Encrypt password with salt
     */
    public static String encryptPassword(String password) {
        try {
            String salt = generateSalt();
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            
            // Combine salt and hashed password
            String hashedPasswordString = Base64.getEncoder().encodeToString(hashedPassword);
            return salt + ":" + hashedPasswordString;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }
    
    /**
     * Verify password against encrypted password
     */
    public static boolean verifyPassword(String password, String encryptedPassword) {
        try {
            String[] parts = encryptedPassword.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            String salt = parts[0];
            String storedHash = parts[1];
            
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            String hashedPasswordString = Base64.getEncoder().encodeToString(hashedPassword);
            
            return storedHash.equals(hashedPasswordString);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }
}