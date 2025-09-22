package com.govschemes.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        TextView welcomeText = findViewById(R.id.welcomeText);
        TextView descriptionText = findViewById(R.id.descriptionText);
        Button exploreButton = findViewById(R.id.exploreButton);

        // Set welcome message
        welcomeText.setText("Welcome to Government Schemes");
        descriptionText.setText("Your gateway to accessing information about various government schemes and benefits available under District Panchayat.");

        // Set button click listener
        exploreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Feature coming soon! Stay tuned for government scheme listings.", Toast.LENGTH_LONG).show();
            }
        });
    }
}