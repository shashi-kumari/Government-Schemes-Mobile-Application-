package com.app.GovernmentSchemes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Admin Add Scheme Activity
 * This activity allows admins to add new schemes to any category.
 * Admin selects a category (Agriculture, Banking, Business, Housing, Education, Health)
 * and fills in scheme details including name, description, notification date, and URL.
 */
public class AdminAddSchemeActivity extends AppCompatActivity {

    private static final String TAG = "AdminAddSchemeActivity";
    private static final String DATABASE_PATH = "Scheme";

    private ImageView backButton;
    private Spinner categorySpinner;
    private TextInputEditText schemeNameInput;
    private TextInputEditText schemeDescriptionInput;
    private TextInputEditText notificationDateInput;
    private TextInputEditText schemeUrlInput;
    private CheckBox sendNotificationCheckbox;
    private Button addSchemeButton;
    private TextView statusText;

    private DatabaseReference schemesReference;
    private ThemeManager themeManager;
    private SchemeSector selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_scheme);

        // Initialize Firebase reference
        schemesReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

        // Initialize views
        initializeViews();

        // Setup category spinner
        setupCategorySpinner();

        // Setup button click listeners
        setupClickListeners();

        // Set default notification date to today
        setDefaultNotificationDate();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.back_button);
        categorySpinner = findViewById(R.id.category_spinner);
        schemeNameInput = findViewById(R.id.scheme_name_input);
        schemeDescriptionInput = findViewById(R.id.scheme_description_input);
        notificationDateInput = findViewById(R.id.notification_date_input);
        schemeUrlInput = findViewById(R.id.scheme_url_input);
        sendNotificationCheckbox = findViewById(R.id.send_notification_checkbox);
        addSchemeButton = findViewById(R.id.add_scheme_button);
        statusText = findViewById(R.id.status_text);
    }

    private void setupCategorySpinner() {
        String[] categories = new String[SchemeSector.values().length];
        for (int i = 0; i < SchemeSector.values().length; i++) {
            categories[i] = SchemeSector.values()[i].getDisplayName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = SchemeSector.values()[position];
                Log.d(TAG, "Selected category: " + selectedCategory.getDisplayName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void setupClickListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addSchemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewScheme();
            }
        });
    }

    private void setDefaultNotificationDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        notificationDateInput.setText(currentDate);
    }

    private void addNewScheme() {
        // Validate inputs
        String schemeName = getTextFromInput(schemeNameInput);
        String schemeDescription = getTextFromInput(schemeDescriptionInput);
        String notificationDate = getTextFromInput(notificationDateInput);
        String schemeUrl = getTextFromInput(schemeUrlInput);

        if (!validateInputs(schemeName, schemeDescription, notificationDate)) {
            return;
        }

        // Set default URL if empty
        if (schemeUrl.isEmpty()) {
            schemeUrl = "na";
        }

        // Validate URL if provided and not 'na'
        if (!schemeUrl.equalsIgnoreCase("na") && !isValidUrl(schemeUrl)) {
            Toast.makeText(this, "Please enter a valid URL (http:// or https://)", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button while saving
        addSchemeButton.setEnabled(false);
        statusText.setText("Adding scheme...");
        statusText.setVisibility(View.VISIBLE);

        // Create scheme data
        SchemeData schemeData = new SchemeData(schemeName, schemeDescription, notificationDate, schemeUrl);

        // Get the category path
        String categoryPath = selectedCategory.getDisplayName();

        // Add scheme to Firebase
        DatabaseReference newSchemeRef = schemesReference.child(categoryPath).push();
        
        final String finalSchemeUrl = schemeUrl;
        newSchemeRef.setValue(schemeData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addSchemeButton.setEnabled(true);
                
                if (task.isSuccessful()) {
                    Log.d(TAG, "Scheme added successfully to " + categoryPath);
                    statusText.setText("Scheme added successfully!");
                    
                    // Clear scheme cache for this category
                    SchemeDataProvider.clearCache();
                    
                    // Send notification if checkbox is checked
                    if (sendNotificationCheckbox.isChecked()) {
                        sendSchemeNotification(schemeName, categoryPath);
                    }
                    
                    Toast.makeText(AdminAddSchemeActivity.this, 
                            "Scheme added to " + categoryPath + " category", 
                            Toast.LENGTH_SHORT).show();
                    
                    // Clear form after successful addition
                    clearForm();
                } else {
                    Log.e(TAG, "Failed to add scheme: " + task.getException());
                    statusText.setText("Failed to add scheme. Please try again.");
                    Toast.makeText(AdminAddSchemeActivity.this,
                            "Failed to add scheme: " + (task.getException() != null ? 
                                    task.getException().getMessage() : "Unknown error"),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTextFromInput(TextInputEditText input) {
        if (input == null || input.getText() == null) {
            return "";
        }
        return input.getText().toString().trim();
    }

    private boolean validateInputs(String schemeName, String schemeDescription, String notificationDate) {
        if (schemeName.isEmpty()) {
            schemeNameInput.setError("Scheme name is required");
            schemeNameInput.requestFocus();
            return false;
        }

        if (schemeDescription.isEmpty()) {
            schemeDescriptionInput.setError("Description is required");
            schemeDescriptionInput.requestFocus();
            return false;
        }

        if (notificationDate.isEmpty()) {
            notificationDateInput.setError("Notification date is required");
            notificationDateInput.requestFocus();
            return false;
        }

        if (selectedCategory == null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Validates URL format - only allows http and https schemes for security
     * @param url URL to validate
     * @return true if valid http/https URL
     */
    private boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        String lowerUrl = url.toLowerCase();
        return (lowerUrl.startsWith("http://") || lowerUrl.startsWith("https://")) 
                && android.util.Patterns.WEB_URL.matcher(url).matches();
    }

    private void sendSchemeNotification(String schemeName, String category) {
        Log.d(TAG, "Sending notification for new scheme: " + schemeName + " in " + category);
        // TODO: Implement Firebase Cloud Messaging to send notification to subscribed users
        // For now, just log the action
        Toast.makeText(this, "Notification will be sent for: " + schemeName, Toast.LENGTH_SHORT).show();
    }

    private void clearForm() {
        schemeNameInput.setText("");
        schemeDescriptionInput.setText("");
        schemeUrlInput.setText("");
        setDefaultNotificationDate();
        sendNotificationCheckbox.setChecked(false);
        // Keep the selected category
    }
}
