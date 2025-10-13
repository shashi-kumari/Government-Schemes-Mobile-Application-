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

public class GovernmentSchemesActivity extends BaseActivity {
    ListView stateListView;
    TextView header_title;

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

        header_title = findViewById(R.id.header_title);
        header_title.setText("Government Schemes");

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
        StateScheme scheme = StateScheme.fromStateName(stateName);
        if (scheme != null && scheme.getUrl() != null && !scheme.getUrl().isEmpty()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scheme.getUrl()));
            startActivity(browserIntent);
        } else {
            Toast.makeText(this, "No scheme URL available for " + stateName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        // Handle any additional clicks specific to this activity
    }
}
