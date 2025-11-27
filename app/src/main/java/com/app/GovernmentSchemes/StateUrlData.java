package com.app.GovernmentSchemes;

/**
 * Model class representing state URL data fetched from Firebase Database.
 * This class is used to deserialize data from the database with structure:
 * {
 *   "code": "CA",
 *   "name": "California",
 *   "url": "https://example.com/ca"
 * }
 */
public class StateUrlData {
    private String code;
    private String name;
    private String url;

    // Default constructor required for Firebase
    public StateUrlData() {
    }

    public StateUrlData(String code, String name, String url) {
        this.code = code;
        this.name = name;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
