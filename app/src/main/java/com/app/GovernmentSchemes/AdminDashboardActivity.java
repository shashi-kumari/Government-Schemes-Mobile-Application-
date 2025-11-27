package com.app.GovernmentSchemes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Admin Dashboard Activity
 * This activity serves as the main dashboard for admin users.
 * Admins can manage users (view, modify, delete) from here.
 */
public class AdminDashboardActivity extends AppCompatActivity {

    private static final String TAG = "AdminDashboardActivity";
    
    private TextView welcomeText;
    private Button manageUsersButton;
    private Button viewSchemesButton;
    private Button logoutButton;
    
    private String adminName;
    private String adminEmail;
    private String adminUuid;
    private ThemeManager themeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        // Get admin data from intent
        Intent intent = getIntent();
        adminName = intent.getStringExtra("name");
        adminEmail = intent.getStringExtra("email");
        adminUuid = intent.getStringExtra("uuid");

        Log.d(TAG, "Admin dashboard loaded for: " + adminEmail);

        // Initialize views
        welcomeText = findViewById(R.id.admin_welcome_text);
        manageUsersButton = findViewById(R.id.manage_users_button);
        viewSchemesButton = findViewById(R.id.view_schemes_button);
        logoutButton = findViewById(R.id.admin_logout_button);

        // Set welcome text
        if (adminName != null && !adminName.isEmpty()) {
            welcomeText.setText("Welcome, " + adminName + " (Admin)");
        } else {
            welcomeText.setText("Welcome, Admin");
        }

        // Set up button click listeners
        manageUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Navigate to user management");
                Intent userListIntent = new Intent(AdminDashboardActivity.this, AdminUserListActivity.class);
                userListIntent.putExtra("adminUuid", adminUuid);
                startActivity(userListIntent);
            }
        });

        viewSchemesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Navigate to main schemes view");
                Intent mainIntent = new Intent(AdminDashboardActivity.this, MainActivity.class);
                mainIntent.putExtra("name", adminName);
                mainIntent.putExtra("email", adminEmail);
                mainIntent.putExtra("uuid", adminUuid);
                mainIntent.putExtra("isAdmin", true);
                mainIntent.putExtra("isGuest", false);
                startActivity(mainIntent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Admin logout");
                Intent logoutIntent = new Intent(AdminDashboardActivity.this, LogoutActivity.class);
                startActivity(logoutIntent);
                finish();
            }
        });
    }
}
