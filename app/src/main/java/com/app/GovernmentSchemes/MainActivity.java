package com.app.GovernmentSchemes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView agriculture, bank, business, education, health, house, logout, subscribeButton, themeToggle, adminDashboardIcon;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_id";
    private static final CharSequence CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESCRIPTION = "My Channel Description";
    private static final int REQUEST_CODE = 1;
    
    private boolean isGuestMode = false;
    private boolean isAdmin = false;
    private ThemeManager themeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
        
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Check if user is in guest mode
        isGuestMode = getIntent().getBooleanExtra("isGuest", false);
        
        // Check if user is admin
        isAdmin = getIntent().getBooleanExtra("isAdmin", false);

        agriculture = findViewById(R.id.agro);
        bank = findViewById(R.id.bank);
        business = findViewById(R.id.business);
        education = findViewById(R.id.education);
        health = findViewById(R.id.health);
        house = findViewById(R.id.house);
        logout = findViewById(R.id.logoutimage);
        themeToggle = findViewById(R.id.theme_toggle);
        adminDashboardIcon = findViewById(R.id.admin_dashboard_icon);
        
        // Show admin icon only for admin users
        if (isAdmin) {
            adminDashboardIcon.setVisibility(View.VISIBLE);
            adminDashboardIcon.setOnClickListener(this);
        }
        
        logout.setOnClickListener(this);
        themeToggle.setOnClickListener(this);
        agriculture.setOnClickListener(this);
        bank.setOnClickListener(this);
        business.setOnClickListener(this);
        education.setOnClickListener(this);
        health.setOnClickListener(this);
        house.setOnClickListener(this);
        subscribeButton = findViewById(R.id.subscribeButton);

        // Create notification channel
        createNotificationChannel();

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNotificationPermission();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE);
        } else {
            showNotification();
        }
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Subscription Complete")
                .setContentText("Stay updated to the new schemes")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotification();
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(agriculture)) {
            Intent agriculture = new Intent(MainActivity.this, com.app.GovernmentSchemes.Agriculture_activity.class);
            startActivity(agriculture);
        }
        if (view.equals(bank)) {
            Intent bank = new Intent(MainActivity.this, banking_activity.class);
            startActivity(bank);
        }
        if (view.equals(business)) {
            Intent business = new Intent(MainActivity.this, business_activity.class);
            startActivity(business);
        }
        if (view.equals(education)) {
            Intent education = new Intent(MainActivity.this, education_activity.class);
            startActivity(education);
        }
        if (view.equals(health)) {
            Intent health = new Intent(MainActivity.this, health_activity.class);
            startActivity(health);
        }
        if (view.equals(house)) {
            Intent housing = new Intent(MainActivity.this, housing_activity.class);
            startActivity(housing);
        }

        if (view.equals(logout)) {
            if (isGuestMode) {
                // For guest mode, go back to welcome screen
                Intent welcome = new Intent(MainActivity.this, WelcomeActivity.class);
                welcome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(welcome);
            } else {
                // For logged in users, use regular logout
                Intent logout = new Intent(MainActivity.this, LogoutActivity.class);
                startActivity(logout);
            }
        }
        
        if (view.equals(adminDashboardIcon)) {
            // Navigate to Admin Dashboard
            Intent adminDashboard = new Intent(MainActivity.this, AdminDashboardActivity.class);
            adminDashboard.putExtra("name", getIntent().getStringExtra("name"));
            adminDashboard.putExtra("email", getIntent().getStringExtra("email"));
            adminDashboard.putExtra("uuid", getIntent().getStringExtra("uuid"));
            startActivity(adminDashboard);
        }
        
        if (view.equals(themeToggle)) {
            toggleTheme();
        }
    }
    
    private void toggleTheme() {
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
}