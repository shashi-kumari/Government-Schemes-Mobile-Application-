package com.app.GovernmentSchemes;

/**
 * Model class representing scheme data fetched from Firebase Database.
 * Each scheme has the following structure:
 * {
 *   "scheme": "Scheme Name",
 *   "description": "Description of the scheme",
 *   "notificationDate": "Date when the scheme was notified",
 *   "url": "URL to the scheme details or 'na' if not available",
 *   "createdAt": timestamp (milliseconds since epoch) when scheme was added
 * }
 */
public class SchemeData {
    private String scheme;
    private String description;
    private String notificationDate;
    private String url;
    private long createdAt;

    // Default constructor required for Firebase
    public SchemeData() {
    }

    public SchemeData(String scheme, String description, String notificationDate, String url) {
        this.scheme = scheme;
        this.description = description;
        this.notificationDate = notificationDate;
        this.url = url;
        this.createdAt = System.currentTimeMillis();
    }

    public SchemeData(String scheme, String description, String notificationDate, String url, long createdAt) {
        this.scheme = scheme;
        this.description = description;
        this.notificationDate = notificationDate;
        this.url = url;
        this.createdAt = createdAt;
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
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

    /**
     * Checks if this scheme was created within the last 24 hours.
     * Note: Schemes with createdAt = 0 (legacy schemes without timestamp) will return false
     * since their actual creation time is unknown.
     * @return true if created within last 24 hours, false for legacy schemes without timestamp
     */
    public boolean isRecentlyAdded() {
        if (createdAt == 0) {
            return false; // Legacy schemes without timestamp are not considered recently added
        }
        long twentyFourHoursAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000);
        return createdAt > twentyFourHoursAgo;
    }
}
