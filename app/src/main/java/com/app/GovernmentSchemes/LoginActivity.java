package com.app.GovernmentSchemes;
import static android.content.ContentValues.TAG;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView signupRedirectText, guestRedirectText;
    private ThemeManager themeManager;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LoginActivity", "Initializing ThemeManager and applying theme");
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        guestRedirectText = findViewById(R.id.guestRedirectText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LoginActivity", "Validation failed for username or password");
                Log.d("LoginActivity", "Validation passed, checking user");

                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, go to MainActivity
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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

    public Boolean validateEmail() {
        String val = loginEmail.getText().toString().trim();
        String emailError = ValidationUtils.getEmailErrorMessage(val);
        if (emailError != null) {
            loginEmail.setError(emailError);
            return false;
        } else {
            loginEmail.setError(null);
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
        String userEmail = loginEmail.getText().toString().trim();
        Log.d("LoginActivity", "Checking user: " + userEmail);
        String userPassword = loginPassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
Log.d("LoginActivity", "onDataChange called for user: " + userEmail);
                if (snapshot.exists()) {
                    Log.d("LoginActivity", "User exists in database");
                    loginEmail.setError(null);
                    String passwordFromDB = null;
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        passwordFromDB = userSnapshot.child("password").getValue(String.class);
                        break;
                    }

                    Log.d("LoginActivity", "Verifying password for user: " + userEmail);
                    // Use encrypted password verification
                    if (passwordFromDB != null && PasswordUtils.verifyPassword(userPassword, passwordFromDB)) {
                        loginEmail.setError(null);
                        String nameFromDB = null, emailFromDB = null, usernameFromDB = null;
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            nameFromDB = userSnapshot.child("name").getValue(String.class);
                            emailFromDB = userSnapshot.child("email").getValue(String.class);
                            usernameFromDB = userSnapshot.child("username").getValue(String.class);
                            break;
}

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", ""); // Don't pass password in intent

                        Log.d("LoginActivity", "Login successful, starting MainActivity");
                        startActivity(intent);
                        finish();
                    } else {
                        Log.d("LoginActivity", "Invalid credentials for user: " + userEmail);
                        loginPassword.setError("Invalid credentials");
                        loginPassword.requestFocus();
                    }
                }else{
                    Log.d("LoginActivity", "User does not exist: " + userEmail);
                    loginEmail.setError("User does not exist");
                    loginEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoginActivity", "Database error: " + error.getMessage());
            }
        });

    }
}