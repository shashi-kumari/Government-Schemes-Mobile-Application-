package com.app.GovernmentSchemes;

/**
 * Model class representing scheme data fetched from Firebase Database.
 * Each scheme has the following structure:
 * {
 *   "scheme": "Scheme Name",
 *   "description": "Description of the scheme",
 *   "notificationDate": "Date when the scheme was notified",
 *   "url": "URL to the scheme details or 'na' if not available"
 * }
 */
public class SchemeData {
    private String scheme;
    private String description;
    private String notificationDate;
    private String url;

    // Default constructor required for Firebase
    public SchemeData() {
    }

    public SchemeData(String scheme, String description, String notificationDate, String url) {
        this.scheme = scheme;
        this.description = description;
        this.notificationDate = notificationDate;
        this.url = url;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Checks if this scheme has a valid URL (not "na" or empty) with a safe scheme (http/https).
     * @return true if the scheme has a valid URL that can be safely opened in a browser
     */
    public boolean hasValidUrl() {
        if (url == null || url.isEmpty() || url.equalsIgnoreCase("na")) {
            return false;
        }
        // Only allow http and https schemes for security
        String lowerUrl = url.toLowerCase();
        return lowerUrl.startsWith("http://") || lowerUrl.startsWith("https://");
    }
}
