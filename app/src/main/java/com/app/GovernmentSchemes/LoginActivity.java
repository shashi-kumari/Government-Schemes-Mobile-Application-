package com.app.GovernmentSchemes;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirectText, guestRedirectText;
    private ThemeManager themeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LoginActivity", "Initializing ThemeManager and applying theme");
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        guestRedirectText = findViewById(R.id.guestRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "Validation failed for username or password");
                Log.d("LoginActivity", "Validation passed, checking user");

                if (!validateUsername() || !validatePassword()) {

                } else {
                    checkUser();
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "Redirecting to SignupActivity");
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        guestRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "Redirecting to MainActivity as guest");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("isGuest", true);
                startActivity(intent);
            }
        });

    }

    public Boolean validateUsername() {
        String val = loginUsername.getText().toString().trim();
        String usernameError = ValidationUtils.getUsernameErrorMessage(val);
        if (usernameError != null) {
            loginUsername.setError(usernameError);
            return false;
        } else {
            loginUsername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginPassword.getText().toString();
        String passwordError = ValidationUtils.getPasswordErrorMessage(val);
        if (passwordError != null) {
            loginPassword.setError(passwordError);
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }


    public void checkUser(){
        String userUsername = loginUsername.getText().toString().trim();
        Log.d("LoginActivity", "Checking user: " + userUsername);
        String userPassword = loginPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
Log.d("LoginActivity", "onDataChange called for user: " + userUsername);
                if (snapshot.exists()) {
                    Log.d("LoginActivity", "User exists in database");
                    loginUsername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    Log.d("LoginActivity", "Verifying password for user: " + userUsername);
                    // Use encrypted password verification
                    if (passwordFromDB != null && PasswordUtils.verifyPassword(userPassword, passwordFromDB)) {
                        loginUsername.setError(null);

                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userUsername).child("email").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", ""); // Don't pass password in intent

                        Log.d("LoginActivity", "Login successful, starting MainActivity");
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("LoginActivity", "Invalid credentials for user: " + userUsername);
                        loginPassword.setError("Invalid credentials");
                        loginPassword.requestFocus();
                    }
                }else{
                    Log.d("LoginActivity", "User does not exist: " + userUsername);
                    loginUsername.setError("User does not exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", "Database error: " + error.getMessage());
            }
        });

    }
}