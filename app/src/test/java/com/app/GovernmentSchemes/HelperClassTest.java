package com.app.GovernmentSchemes;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for HelperClass
 */
public class HelperClassTest {

    @Test
    public void testDefaultConstructor() {
        HelperClass helper = new HelperClass();
        assertFalse("Default admin access should be false", helper.getAdmnAccess());
        assertNull("Default name should be null", helper.getName());
        assertNull("Default email should be null", helper.getEmail());
        assertNull("Default username should be null", helper.getUsername());
        assertNull("Default password should be null", helper.getPassword());
        assertNull("Default uuid should be null", helper.getUuid());
    }

    @Test
    public void testParameterizedConstructor() {
        String name = "John Doe";
        String email = "john@example.com";
        String username = "johndoe";
        String password = "password123";

        HelperClass helper = new HelperClass(name, email, username, password);

        assertEquals("Name should match", name, helper.getName());
        assertEquals("Email should match", email, helper.getEmail());
        assertEquals("Username should match", username, helper.getUsername());
        assertEquals("Password should match", password, helper.getPassword());
        assertFalse("Admin access should be false by default", helper.getAdmnAccess());
    }

    @Test
    public void testSettersAndGetters() {
        HelperClass helper = new HelperClass();

        helper.setName("Jane Doe");
        assertEquals("Name should be set correctly", "Jane Doe", helper.getName());

        helper.setEmail("jane@example.com");
        assertEquals("Email should be set correctly", "jane@example.com", helper.getEmail());

        helper.setUsername("janedoe");
        assertEquals("Username should be set correctly", "janedoe", helper.getUsername());

        helper.setPassword("securepassword");
        assertEquals("Password should be set correctly", "securepassword", helper.getPassword());
    }

    @Test
    public void testUuidGetterSetter() {
        HelperClass helper = new HelperClass();

        String uuid = "abc123-def456-ghi789";
        helper.setUuid(uuid);

        assertEquals("UUID should be set correctly", uuid, helper.getUuid());
    }

    @Test
    public void testAdminAccessGetterSetter() {
        HelperClass helper = new HelperClass();

        // Default should be false
        assertFalse("Default admin access should be false", helper.getAdmnAccess());

        // Set to true
        helper.setAdmnAccess(true);
        assertTrue("Admin access should be true after setting", helper.getAdmnAccess());

        // Set back to false
        helper.setAdmnAccess(false);
        assertFalse("Admin access should be false after setting", helper.getAdmnAccess());
    }

    @Test
    public void testAdminAccessWithConstructor() {
        HelperClass helper = new HelperClass("Admin User", "admin@example.com", "adminuser", "adminpass");
        
        // Default should be false even after parameterized constructor
        assertFalse("Admin access should be false by default", helper.getAdmnAccess());
        
        // Set admin access
        helper.setAdmnAccess(true);
        assertTrue("Admin access should be true after setting", helper.getAdmnAccess());
    }

    @Test
    public void testUuidWithUserData() {
        String name = "Test User";
        String email = "test@example.com";
        String username = "testuser";
        String password = "testpass";
        String uuid = "firebase-generated-uuid-12345";

        HelperClass helper = new HelperClass(name, email, username, password);
        helper.setUuid(uuid);
        helper.setAdmnAccess(true);

        assertEquals("Name should match", name, helper.getName());
        assertEquals("Email should match", email, helper.getEmail());
        assertEquals("Username should match", username, helper.getUsername());
        assertEquals("Password should match", password, helper.getPassword());
        assertEquals("UUID should match", uuid, helper.getUuid());
        assertTrue("Admin access should be true", helper.getAdmnAccess());
    }
}
