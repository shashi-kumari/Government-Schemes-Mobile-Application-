package com.app.GovernmentSchemes;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// In your LogoutActivity or any relevant activity
public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Clear user session or credentials
        clearUserSession();

        // Navigate back to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Toast.makeText(getBaseContext(),"Logged out successfully",Toast.LENGTH_SHORT).show();
        startActivity(intent);

        // Finish the current activity
        finish();
    }

    private void clearUserSession() {
        // Clear any stored user session data or credentials
        // For app, you can use SharedPreferences or other storage mechanism
        SharedPreferences preferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
