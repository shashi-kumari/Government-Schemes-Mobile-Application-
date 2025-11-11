package com.app.GovernmentSchemes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    
    protected ThemeManager themeManager;
    protected ImageView themeToggle;
    protected ImageView logout;
    protected ImageView menuToggle;
    protected ImageView notificationIcon;
    protected boolean isGuestMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
    }

    protected void setupCommonViews() {
        // Check if user is in guest mode
        isGuestMode = getIntent().getBooleanExtra("isGuest", false);
        
        // Setup common navigation elements if they exist in the layout
        themeToggle = findViewById(R.id.theme_toggle);
        logout = findViewById(R.id.logout_icon);
        menuToggle = findViewById(R.id.menu_toggle);
        notificationIcon = findViewById(R.id.notification_icon);
        
        if (themeToggle != null) {
            themeToggle.setOnClickListener(this);
        }
        
        if (logout != null) {
            logout.setOnClickListener(this);
        }
        
        if (menuToggle != null) {
            menuToggle.setOnClickListener(this);
        }
        
        if (notificationIcon != null) {
            notificationIcon.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(themeToggle)) {
            toggleTheme();
        } else if (view.equals(logout)) {
            handleLogout();
        } else if (view.equals(menuToggle)) {
            handleMenuToggle();
        } else if (view.equals(notificationIcon)) {
            handleNotificationClick();
        }
    }
    
    protected void toggleTheme() {
        int currentTheme = themeManager.getTheme();
        int newTheme;
        
        // Cycle through themes: System -> Light -> Dark -> System
        switch (currentTheme) {
            case ThemeManager.THEME_SYSTEM:
                newTheme = ThemeManager.THEME_LIGHT;
                break;
            case ThemeManager.THEME_LIGHT:
                newTheme = ThemeManager.THEME_DARK;
                break;
            case ThemeManager.THEME_DARK:
            default:
                newTheme = ThemeManager.THEME_SYSTEM;
                break;
        }
        
        themeManager.setTheme(newTheme);
        // Recreate activity to apply theme change
        recreate();
    }
    
    protected void handleLogout() {
        if (isGuestMode) {
            // For guest mode, go back to welcome screen
            Intent welcome = new Intent(this, WelcomeActivity.class);
            welcome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(welcome);
        } else {
            // For logged in users, use regular logout
            Intent logout = new Intent(this, LogoutActivity.class);
            startActivity(logout);
        }
    }
    
    protected void handleMenuToggle() {
        // Go back to main activity
        Intent main = new Intent(this, MainActivity.class);
        main.putExtra("isGuest", isGuestMode);
        startActivity(main);
    }
    
    protected void handleNotificationClick() {
        // Go to notification list activity
        Intent notificationList = new Intent(this, NotificationListActivity.class);
        notificationList.putExtra("isGuest", isGuestMode);
        startActivity(notificationList);
    }
}