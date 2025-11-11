package com.app.GovernmentSchemes;

public enum SchemeSector {
    AGRICULTURE("Agriculture"),
    BANKING("Banking"),
    BUSINESS("Business"),
    EDUCATION("Education"),
    HEALTH("Health"),
    HOUSING("Housing");

    private final String displayName;

    SchemeSector(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
