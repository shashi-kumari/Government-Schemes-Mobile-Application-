package com.app.GovernmentSchemes;

import android.util.Patterns;

/**
 * Utility class for input validation
 */
public class ValidationUtils {
    
    /**
     * Validate if name is not empty and contains only letters and spaces
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Allow letters, spaces, and common name characters
        return name.trim().matches("^[a-zA-Z\\s.'-]+$") && name.trim().length() >= 2;
    }
    
    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }
    
    /**
     * Validate username format (alphanumeric, underscore, minimum 3 characters)
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        // Username should be 3-20 characters, alphanumeric and underscore only
        return username.trim().matches("^[a-zA-Z0-9_]{3,20}$");
    }
    
    /**
     * Validate password strength
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        // Password should be at least 6 characters long
        return password.length() >= 6;
    }
    
    /**
     * Get validation error message for name
     */
    public static String getNameErrorMessage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required";
        }
        if (name.trim().length() < 2) {
            return "Name must be at least 2 characters long";
        }
        if (!name.trim().matches("^[a-zA-Z\\s.'-]+$")) {
            return "Name can only contain letters, spaces, and common punctuation";
        }
        return null;
    }
    
    /**
     * Get validation error message for email
     */
    public static String getEmailErrorMessage(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email is required";
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            return "Please enter a valid email address";
        }
        return null;
    }
    
    /**
     * Get validation error message for username
     */
    public static String getUsernameErrorMessage(String username) {
        if (username == null || username.trim().isEmpty()) {
            return "Username is required";
        }
        if (username.trim().length() < 3) {
            return "Username must be at least 3 characters long";
        }
        if (username.trim().length() > 20) {
            return "Username must be no more than 20 characters long";
        }
        if (!username.trim().matches("^[a-zA-Z0-9_]+$")) {
            return "Username can only contain letters, numbers, and underscores";
        }
        return null;
    }
    
    /**
     * Get validation error message for password
     */
    public static String getPasswordErrorMessage(String password) {
        if (password == null || password.isEmpty()) {
            return "Password is required";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters long";
        }
        return null;
    }
}