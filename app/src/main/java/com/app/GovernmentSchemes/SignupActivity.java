package com.app.GovernmentSchemes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, signupPassword;
    TextView loginRedirectText;
    Button signupButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    private ThemeManager themeManager;
    private static final String TAG = "SignupActivity";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        // Inside onClick of signupButton
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Signup button clicked. Validating fields.");
                if (validateAllFields()) {
                    String name = signupName.getText().toString().trim();
                    String email = signupEmail.getText().toString().trim();
                    String username = signupUsername.getText().toString().trim();
                    String password = signupPassword.getText().toString();

                    // 1\) Check username first
                    checkUsernameAvailability(name, email, username, password);
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Validate all input fields
     */
    private boolean validateAllFields() {
        boolean isValid = true;

        // Validate name
        String name = signupName.getText().toString().trim();
        String nameError = ValidationUtils.getNameErrorMessage(name);
        if (nameError != null) {
            signupName.setError(nameError);
            isValid = false;
        } else {
            signupName.setError(null);
        }

        // Validate email
        String email = signupEmail.getText().toString().trim();
        String emailError = ValidationUtils.getEmailErrorMessage(email);
        if (emailError != null) {
            signupEmail.setError(emailError);
            isValid = false;
        } else {
            signupEmail.setError(null);
        }

        // Validate username
        String username = signupUsername.getText().toString().trim();
        String usernameError = ValidationUtils.getUsernameErrorMessage(username);
        if (usernameError != null) {
            signupUsername.setError(usernameError);
            isValid = false;
        } else {
            signupUsername.setError(null);
        }

        // Validate password
        String password = signupPassword.getText().toString();
        String passwordError = ValidationUtils.getPasswordErrorMessage(password);
        if (passwordError != null) {
            signupPassword.setError(passwordError);
            isValid = false;
        } else {
            signupPassword.setError(null);
        }

        return isValid;
    }

    /**
     * Check if username is already taken
     */
    private void checkUsernameAvailability(String name, String email, String username, String password) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "Username already exists: " + username);
                    signupUsername.setError("Username already exists. Please choose a different one.");
                    signupUsername.requestFocus();
                } else {
                    Log.d(TAG, "Username available: " + username);
                    // Username is available, proceed with registration
                    registerUser(name, email, username, password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(SignupActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Update checkUsernameAvailability to only call Firebase Auth on success
    private void registerUser(String name, String email, String username, String password) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        // 2\) First create user in Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        try {
                            String uuid = mAuth.getCurrentUser().getUid();

                            // 3\) Encrypt password before saving to Realtime DB
                            String encryptedPassword = PasswordUtils.encryptPassword(password);

                            HelperClass helperClass =
                                    new HelperClass(name, email, username, encryptedPassword);

                            // 4\) Save user data in Realtime DB
                            reference.child(uuid)
                                    .setValue(helperClass)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SignupActivity.this,
                                                "You have signed up successfully!",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignupActivity.this,
                                                "Registration failed: " + e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SignupActivity.this,
                                    "Error during registration: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}