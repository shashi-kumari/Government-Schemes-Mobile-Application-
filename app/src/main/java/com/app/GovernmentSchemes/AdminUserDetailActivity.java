package com.app.GovernmentSchemes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Admin User Detail Activity
 * Allows admin to view, modify, or delete user details.
 */
public class AdminUserDetailActivity extends AppCompatActivity {

    private static final String TAG = "AdminUserDetailActivity";

    private ImageView backButton;
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private CheckBox adminCheckBox;
    private Button saveButton;
    private Button deleteButton;

    private DatabaseReference userReference;
    private String userUuid;
    private String adminUuid;
    private HelperClass currentUser;
    private ThemeManager themeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_detail);

        // Get data from intent
        userUuid = getIntent().getStringExtra("userUuid");
        adminUuid = getIntent().getStringExtra("adminUuid");

        if (userUuid == null) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize Firebase reference
        userReference = FirebaseDatabase.getInstance().getReference("users").child(userUuid);

        // Initialize views
        backButton = findViewById(R.id.back_button);
        nameEditText = findViewById(R.id.edit_name);
        emailEditText = findViewById(R.id.edit_email);
        usernameEditText = findViewById(R.id.edit_username);
        adminCheckBox = findViewById(R.id.admin_checkbox);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);

        // Set up back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set up save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserChanges();
            }
        });

        // Set up delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAndDeleteUser();
            }
        });

        // Load user data
        loadUserData();
    }

    private void loadUserData() {
        Log.d(TAG, "Loading user data for UUID: " + userUuid);

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentUser = snapshot.getValue(HelperClass.class);
                    if (currentUser != null) {
                        currentUser.setUuid(userUuid);
                        populateFields();
                    } else {
                        Toast.makeText(AdminUserDetailActivity.this, 
                            "Failed to load user data", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(AdminUserDetailActivity.this, 
                        "User not found", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to load user: " + error.getMessage());
                Toast.makeText(AdminUserDetailActivity.this, 
                    "Failed to load user: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void populateFields() {
        if (currentUser != null) {
            nameEditText.setText(currentUser.getName());
            emailEditText.setText(currentUser.getEmail());
            usernameEditText.setText(currentUser.getUsername());
            adminCheckBox.setChecked(currentUser.getAdmnAccess());

            // Prevent admin from removing their own admin access
            if (userUuid.equals(adminUuid)) {
                adminCheckBox.setEnabled(false);
                deleteButton.setEnabled(false);
                deleteButton.setAlpha(0.5f);
            }
        }
    }

    private void saveUserChanges() {
        String newName = nameEditText.getText().toString().trim();
        String newEmail = emailEditText.getText().toString().trim();
        String newUsername = usernameEditText.getText().toString().trim();
        boolean newAdminAccess = adminCheckBox.isChecked();

        // Validate input
        String nameError = ValidationUtils.getNameErrorMessage(newName);
        if (nameError != null) {
            nameEditText.setError(nameError);
            return;
        }

        String emailError = ValidationUtils.getEmailErrorMessage(newEmail);
        if (emailError != null) {
            emailEditText.setError(emailError);
            return;
        }

        String usernameError = ValidationUtils.getUsernameErrorMessage(newUsername);
        if (usernameError != null) {
            usernameEditText.setError(usernameError);
            return;
        }

        Log.d(TAG, "Saving user changes for UUID: " + userUuid);

        // Update user data in Firebase
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", newName);
        updates.put("email", newEmail);
        updates.put("username", newUsername);
        updates.put("admnAccess", newAdminAccess);

        userReference.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User updated successfully");
                    Toast.makeText(AdminUserDetailActivity.this, 
                        "User updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(TAG, "Failed to update user", task.getException());
                    Toast.makeText(AdminUserDetailActivity.this, 
                        "Failed to update user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void confirmAndDeleteUser() {
        // Prevent admin from deleting themselves
        if (userUuid.equals(adminUuid)) {
            Toast.makeText(this, "Cannot delete your own account", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
            .setTitle("Delete User")
            .setMessage("Are you sure you want to delete this user? This action cannot be undone.")
            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteUser();
                }
            })
            .setNegativeButton("Cancel", null)
            .show();
    }

    private void deleteUser() {
        Log.d(TAG, "Deleting user with UUID: " + userUuid);

        userReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "User deleted successfully");
                    Toast.makeText(AdminUserDetailActivity.this, 
                        "User deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(TAG, "Failed to delete user", task.getException());
                    Toast.makeText(AdminUserDetailActivity.this, 
                        "Failed to delete user", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
