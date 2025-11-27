package com.app.GovernmentSchemes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Admin URL Management Activity
 * This activity allows admins to manage scheme URLs for different categories and states.
 * Admin can select a category (Education, Banking, Agriculture, etc.) from a dropdown
 * and view/modify URLs for each state.
 */
public class AdminUrlManagementActivity extends AppCompatActivity {

    private static final String TAG = "AdminUrlManagementActivity";

    private ImageView backButton;
    private Spinner categorySpinner;
    private RecyclerView stateUrlsRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyTextView;

    private StateUrlAdapter stateUrlAdapter;
    private List<StateUrlItem> stateUrlList;
    private DatabaseReference urlsReference;
    private ThemeManager themeManager;

    private SchemeSector selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_url_management);

        // Initialize Firebase reference
        urlsReference = FirebaseDatabase.getInstance().getReference("urls");

        // Initialize views
        backButton = findViewById(R.id.back_button);
        categorySpinner = findViewById(R.id.category_spinner);
        stateUrlsRecyclerView = findViewById(R.id.state_urls_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        emptyTextView = findViewById(R.id.empty_text_view);

        // Setup back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Setup category spinner
        setupCategorySpinner();

        // Setup RecyclerView
        stateUrlList = new ArrayList<>();
        stateUrlAdapter = new StateUrlAdapter(stateUrlList);
        stateUrlsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        stateUrlsRecyclerView.setAdapter(stateUrlAdapter);

        // Setup swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadStateUrls();
            }
        });
    }

    /**
     * Sets up the category dropdown spinner with available scheme sectors
     */
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
                loadStateUrls();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    /**
     * Loads state URLs for the selected category from Firebase
     */
    private void loadStateUrls() {
        if (selectedCategory == null) {
            return;
        }

        Log.d(TAG, "Loading URLs for category: " + selectedCategory.getDisplayName());
        swipeRefreshLayout.setRefreshing(true);

        String categoryKey = selectedCategory.name().toLowerCase();
        urlsReference.child(categoryKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stateUrlList.clear();

                // Load states from StateScheme enum as base
                for (StateScheme state : StateScheme.values()) {
                    String stateCode = getStateCode(state.getStateName());
                    String stateName = state.getStateName();
                    
                    // Check if there's a custom URL in Firebase
                    String url = "";
                    DataSnapshot stateSnapshot = snapshot.child(stateCode);
                    if (stateSnapshot.exists()) {
                        String firebaseUrl = stateSnapshot.child("urls").getValue(String.class);
                        if (firebaseUrl != null) {
                            url = firebaseUrl;
                        }
                    }

                    stateUrlList.add(new StateUrlItem(stateCode, stateName, url));
                }

                Log.d(TAG, "Loaded " + stateUrlList.size() + " state URLs");

                stateUrlAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

                // Show empty state if no URLs
                if (stateUrlList.isEmpty()) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    stateUrlsRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    stateUrlsRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to load URLs: " + error.getMessage());
                Toast.makeText(AdminUrlManagementActivity.this,
                        "Failed to load URLs: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Generates a state code from the state name
     * @param stateName Full name of the state
     * @return Two-letter state code
     */
    private String getStateCode(String stateName) {
        if (stateName == null || stateName.isEmpty()) {
            return "XX";
        }
        
        // Trim and normalize the state name, handle consecutive spaces
        String normalizedName = stateName.trim().replaceAll("\\s+", " ");
        if (normalizedName.isEmpty()) {
            return "XX";
        }
        
        String[] words = normalizedName.split(" ");
        if (words.length >= 2 && !words[0].isEmpty() && !words[1].isEmpty()) {
            return (words[0].substring(0, 1) + words[1].substring(0, 1)).toUpperCase();
        } else if (!words[0].isEmpty()) {
            return words[0].substring(0, Math.min(2, words[0].length())).toUpperCase();
        } else {
            return "XX";
        }
    }

    /**
     * Saves a URL for a specific state and category to Firebase
     * @param stateCode State code
     * @param stateName State name
     * @param url URL to save
     */
    private void saveStateUrl(String stateCode, String stateName, String url) {
        if (selectedCategory == null) {
            return;
        }

        String categoryKey = selectedCategory.name().toLowerCase();
        DatabaseReference stateRef = urlsReference.child(categoryKey).child(stateCode);

        StateUrlData data = new StateUrlData(stateCode, stateName, url);
        stateRef.setValue(data)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "URL saved successfully for " + stateName);
                    Toast.makeText(AdminUrlManagementActivity.this,
                            "URL saved for " + stateName,
                            Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to save URL: " + e.getMessage());
                    Toast.makeText(AdminUrlManagementActivity.this,
                            "Failed to save URL: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Data class for state URL items displayed in the list
     */
    private static class StateUrlItem {
        String code;
        String name;
        String url;

        StateUrlItem(String code, String name, String url) {
            this.code = code;
            this.name = name;
            this.url = url;
        }
    }

    public static class StateUrlData {
        public String code;
        public String name;
        public String url;

        public StateUrlData() {
            // Default constructor required for Firebase
        }

        public StateUrlData(String code, String name, String url) {
            this.code = code;
            this.name = name;
            this.url = url;
        }
    }

    /**
     * Adapter for displaying state URLs in RecyclerView
     */
    private class StateUrlAdapter extends RecyclerView.Adapter<StateUrlAdapter.StateUrlViewHolder> {

        private final List<StateUrlItem> items;

        StateUrlAdapter(List<StateUrlItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public StateUrlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_state_url, parent, false);
            return new StateUrlViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StateUrlViewHolder holder, int position) {
            StateUrlItem item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class StateUrlViewHolder extends RecyclerView.ViewHolder {
            TextView stateCodeText;
            TextView stateNameText;
            TextInputEditText urlEditText;
            Button saveButton;

            StateUrlViewHolder(@NonNull View itemView) {
                super(itemView);
                stateCodeText = itemView.findViewById(R.id.state_code_text);
                stateNameText = itemView.findViewById(R.id.state_name_text);
                urlEditText = itemView.findViewById(R.id.url_edit_text);
                saveButton = itemView.findViewById(R.id.save_button);
            }

            void bind(StateUrlItem item) {
                stateCodeText.setText(item.code);
                stateNameText.setText(item.name);
                urlEditText.setText(item.url);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newUrl = urlEditText.getText() != null ? 
                                urlEditText.getText().toString().trim() : "";
                        
                        if (newUrl.isEmpty()) {
                            Toast.makeText(AdminUrlManagementActivity.this,
                                    "Please enter a URL",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!isValidUrl(newUrl)) {
                            Toast.makeText(AdminUrlManagementActivity.this,
                                    "Please enter a valid URL",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        saveStateUrl(item.code, item.name, newUrl);
                        item.url = newUrl;
                    }
                });
            }

            /**
             * Validates URL format using Android's Patterns.WEB_URL
             * @param url URL to validate
             * @return true if valid, false otherwise
             */
            private boolean isValidUrl(String url) {
                return android.util.Patterns.WEB_URL.matcher(url).matches();
            }
        }
    }
}
