package com.app.GovernmentSchemes;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_THEME = "selected_theme";
    
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_SYSTEM = 2;

    private SharedPreferences prefs;

    public ThemeManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setTheme(int theme) {
        prefs.edit().putInt(KEY_THEME, theme).apply();
        applyTheme(theme);
    }

    public int getTheme() {
        return prefs.getInt(KEY_THEME, THEME_SYSTEM);
    }

    public void applyTheme(int theme) {
        switch (theme) {
            case THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case THEME_SYSTEM:
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    public void applyCurrentTheme() {
        applyTheme(getTheme());
    }
}