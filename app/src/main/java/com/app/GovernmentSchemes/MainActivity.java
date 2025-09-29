package com.app.GovernmentSchemes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    ImageView agriculture, bank, business, education, health, house, subscribeButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "channel_id";
    private static final CharSequence CHANNEL_NAME = "My Channel";
    private static final String CHANNEL_DESCRIPTION = "My Channel Description";
    private static final int REQUEST_CODE = 1;
    
    private boolean isGuestMode = false;
    private ThemeManager themeManager;
    private String userName = "";
    private String userEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
        
        setContentView(R.layout.activity_main);

        // Setup toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        
        navigationView.setNavigationItemSelectedListener(this);

        // Check if user is in guest mode and get user data
        isGuestMode = getIntent().getBooleanExtra("isGuest", false);
        userName = getIntent().getStringExtra("name");
        userEmail = getIntent().getStringExtra("email");
        
        // Setup navigation header with user info
        setupNavigationHeader();

        // Initialize views
        agriculture = findViewById(R.id.agro);
        bank = findViewById(R.id.bank);
        business = findViewById(R.id.business);
        education = findViewById(R.id.education);
        health = findViewById(R.id.health);
        house = findViewById(R.id.house);
        subscribeButton = findViewById(R.id.subscribeButton);
        
        // Set click listeners
        agriculture.setOnClickListener(this);
        bank.setOnClickListener(this);
        business.setOnClickListener(this);
        education.setOnClickListener(this);
        health.setOnClickListener(this);
        house.setOnClickListener(this);

        // Create notification channel
        createNotificationChannel();

        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNotificationPermission();
            }
        });
    }
    
    private void setupNavigationHeader() {
        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = headerView.findViewById(R.id.nav_user_name);
        TextView navUserEmail = headerView.findViewById(R.id.nav_user_email);
        
        if (isGuestMode) {
            navUserName.setText(getString(R.string.guest_user));
            navUserEmail.setText("");
        } else {
            navUserName.setText(userName != null && !userName.isEmpty() ? userName : getString(R.string.user_name_placeholder));
            navUserEmail.setText(userEmail != null ? userEmail : "");
        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.nav_theme_toggle) {
            toggleTheme();
        } else if (id == R.id.nav_logout) {
            handleLogout();
        }
        
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
    }
    
    private void handleLogout() {
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