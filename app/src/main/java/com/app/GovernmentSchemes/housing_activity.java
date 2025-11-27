package com.app.GovernmentSchemes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class housing_activity extends AppCompatActivity {

    private ImageView backButton;
    private ImageView notificationIcon;
    private ImageView themeToggle;
    private TextView headerTitle;
    private TextView headerInfoText;
    private TextView emptyTextView;
    private Button govtSchemesButton;
    private RecyclerView schemesRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SchemeAdapter schemeAdapter;
    private List<SchemeData> schemeList;
    private ThemeManager themeManager;
    private boolean isGuestMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector_schemes);

        // Check if user is in guest mode
        isGuestMode = getIntent().getBooleanExtra("isGuest", false);

        // Initialize views
        initializeViews();

        // Setup RecyclerView
        schemeList = new ArrayList<>();
        schemeAdapter = new SchemeAdapter(schemeList, SchemeSector.HOUSING.getDisplayName());
        schemesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        schemesRecyclerView.setAdapter(schemeAdapter);

        // Setup swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SchemeDataProvider.clearCache();
                loadSchemesFromDatabase();
            }
        });

        // Load schemes
        loadSchemesFromDatabase();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.back_button);
        notificationIcon = findViewById(R.id.notification_icon);
        themeToggle = findViewById(R.id.theme_toggle);
        headerTitle = findViewById(R.id.header_title);
        headerInfoText = findViewById(R.id.header_info_text);
        emptyTextView = findViewById(R.id.empty_text_view);
        govtSchemesButton = findViewById(R.id.govt_schemes_button);
        schemesRecyclerView = findViewById(R.id.schemes_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        headerTitle.setText("Housing Sector");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationList = new Intent(housing_activity.this, NotificationListActivity.class);
                notificationList.putExtra("isGuest", isGuestMode);
                startActivity(notificationList);
            }
        });

        themeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTheme();
            }
        });

        govtSchemesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent govtSchemes = new Intent(housing_activity.this, GovernmentSchemesActivity.class);
                govtSchemes.putExtra(GovernmentSchemesActivity.EXTRA_SECTOR, SchemeSector.HOUSING.name());
                startActivity(govtSchemes);
            }
        });
    }

    private void toggleTheme() {
        int currentTheme = themeManager.getTheme();
        int newTheme;

        switch (currentTheme) {
            case ThemeManager.THEME_SYSTEM:
                newTheme = ThemeManager.THEME_LIGHT;
                break;
            case ThemeManager.THEME_LIGHT:
                newTheme = ThemeManager.THEME_DARK;
                break;
            case ThemeManager.THEME_DARK:
            default:
                newTheme = ThemeManager.THEME_SYSTEM;
                break;
        }

        themeManager.setTheme(newTheme);
        recreate();
    }

    private void loadSchemesFromDatabase() {
        swipeRefreshLayout.setRefreshing(true);
        schemeList.clear();

        SchemeDataProvider.getSchemesForSector(SchemeSector.HOUSING, new SchemeDataProvider.SchemeListCallback() {
            @Override
            public void onSchemesLoaded(List<SchemeData> schemes) {
                schemeList.addAll(schemes);
                updateUI();
            }

            @Override
            public void onError(String errorMessage) {
                swipeRefreshLayout.setRefreshing(false);
                headerInfoText.setText("Error loading schemes: " + errorMessage);
                Toast.makeText(housing_activity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI() {
        schemeAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

        if (schemeList.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            schemesRecyclerView.setVisibility(View.GONE);
            headerInfoText.setText("No schemes available");
        } else {
            emptyTextView.setVisibility(View.GONE);
            schemesRecyclerView.setVisibility(View.VISIBLE);
            headerInfoText.setText(schemeList.size() + " scheme(s) available");
        }
    }

    /**
     * Adapter for displaying schemes in RecyclerView
     */
    private class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.SchemeViewHolder> {

        private final List<SchemeData> items;
        private final String sectorName;

        SchemeAdapter(List<SchemeData> items, String sectorName) {
            this.items = items;
            this.sectorName = sectorName;
        }

        @NonNull
        @Override
        public SchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sector_scheme, parent, false);
            return new SchemeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SchemeViewHolder holder, int position) {
            SchemeData item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class SchemeViewHolder extends RecyclerView.ViewHolder {
            TextView categoryBadge;
            TextView schemeNameText;
            TextView schemeDescriptionText;
            TextView notificationDateText;
            Button viewDetailsButton;

            SchemeViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryBadge = itemView.findViewById(R.id.category_badge);
                schemeNameText = itemView.findViewById(R.id.scheme_name_text);
                schemeDescriptionText = itemView.findViewById(R.id.scheme_description_text);
                notificationDateText = itemView.findViewById(R.id.notification_date_text);
                viewDetailsButton = itemView.findViewById(R.id.view_details_button);
            }

            void bind(SchemeData item) {
                categoryBadge.setText(sectorName);
                schemeNameText.setText(item.getScheme() != null ? item.getScheme() : "");
                schemeDescriptionText.setText(item.getDescription() != null ? item.getDescription() : "");
                
                String notificationDate = item.getNotificationDate();
                if (notificationDate != null && !notificationDate.isEmpty()) {
                    notificationDateText.setVisibility(View.VISIBLE);
                    notificationDateText.setText("Notification: " + notificationDate);
                } else {
                    notificationDateText.setVisibility(View.GONE);
                }

                // Setup view details button
                if (item.hasValidUrl()) {
                    viewDetailsButton.setVisibility(View.VISIBLE);
                    viewDetailsButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openUrl(item.getUrl());
                        }
                    });
                } else {
                    viewDetailsButton.setVisibility(View.GONE);
                }
            }

            private void openUrl(String url) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(housing_activity.this,
                            "Could not open URL",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}