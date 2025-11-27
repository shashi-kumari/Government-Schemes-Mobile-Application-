package com.app.GovernmentSchemes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class GovernmentSchemesActivity extends BaseActivity {
    public static final String EXTRA_SECTOR = "sector";
    
    ListView stateListView;
    TextView header_title;
    SchemeSector sector;

    // List of all Indian states and Union Territories
    String[] indianStates = {
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal",
            "Andaman and Nicobar Islands",
            "Chandigarh",
            "Dadra and Nagar Haveli and Daman and Diu",
            "Delhi",
            "Jammu and Kashmir",
            "Ladakh",
            "Lakshadweep",
            "Puducherry"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_government_schemes);

        // Setup common navigation elements
        setupCommonViews();
        
        // Get the sector from intent, default to AGRICULTURE if not provided
        String sectorName = getIntent().getStringExtra(EXTRA_SECTOR);
        if (sectorName != null) {
            try {
                sector = SchemeSector.valueOf(sectorName);
            } catch (IllegalArgumentException e) {
                sector = SchemeSector.AGRICULTURE;
            }
        } else {
            sector = SchemeSector.AGRICULTURE;
        }

        header_title = findViewById(R.id.header_title);
        header_title.setText(sector.getDisplayName() + " - State Schemes");

        stateListView = findViewById(R.id.state_list_view);

        // Create adapter for the list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                indianStates
        );

        stateListView.setAdapter(adapter);

        // Set click listener for list items
        stateListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedState = indianStates[position];
                handleStateClick(selectedState);
            }
        });
    }

    private void handleStateClick(String stateName) {
        // Fetch URL from Firebase Database asynchronously
        StateSchemeProvider.getStateUrl(sector, stateName, new StateSchemeProvider.StateUrlCallback() {
            @Override
            public void onUrlLoaded(@Nullable String url) {
                if (url != null && !url.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(GovernmentSchemesActivity.this, 
                            "No " + sector.getDisplayName().toLowerCase() + " scheme URL available for " + stateName, 
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(GovernmentSchemesActivity.this, 
                        "Error loading URL: " + errorMessage, 
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        // Handle any additional clicks specific to this activity
    }
}
