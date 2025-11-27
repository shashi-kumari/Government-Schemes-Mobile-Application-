package com.app.GovernmentSchemes;


public class HelperClass {

    private boolean admnAccess;
    private String uuid;
    String name, email, username, password;

    // Default constructor required for Firebase
    public HelperClass() {
        this.admnAccess = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HelperClass(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.admnAccess = false;
    }

    public void setAdmnAccess(boolean admnAccess) {
        this.admnAccess = admnAccess;
    }

    public boolean getAdmnAccess() {
        return admnAccess;
    }
}