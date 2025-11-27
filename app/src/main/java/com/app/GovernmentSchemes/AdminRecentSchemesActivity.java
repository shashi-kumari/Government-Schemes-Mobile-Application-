package com.app.GovernmentSchemes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Admin Recent Schemes Activity
 * This activity displays schemes added in the last 24 hours across all categories.
 * Admin can view recent schemes and send notifications for selected schemes.
 */
public class AdminRecentSchemesActivity extends AppCompatActivity {

    private static final String TAG = "AdminRecentSchemesActivity";
    private static final String DATABASE_PATH = "Scheme";
    private static final long TWENTY_FOUR_HOURS_MS = 24 * 60 * 60 * 1000;

    private ImageView backButton;
    private RecyclerView recentSchemesRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyTextView;
    private TextView headerInfoText;

    private RecentSchemeAdapter recentSchemeAdapter;
    private List<RecentSchemeItem> recentSchemeList;
    private DatabaseReference schemesReference;
    private ThemeManager themeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_recent_schemes);

        // Initialize Firebase reference
        schemesReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

        // Initialize views
        initializeViews();

        // Setup RecyclerView
        recentSchemeList = new ArrayList<>();
        recentSchemeAdapter = new RecentSchemeAdapter(recentSchemeList);
        recentSchemesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recentSchemesRecyclerView.setAdapter(recentSchemeAdapter);

        // Setup swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRecentSchemes();
            }
        });

        // Load recent schemes
        loadRecentSchemes();
    }

    private void initializeViews() {
        backButton = findViewById(R.id.back_button);
        recentSchemesRecyclerView = findViewById(R.id.recent_schemes_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        emptyTextView = findViewById(R.id.empty_text_view);
        headerInfoText = findViewById(R.id.header_info_text);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadRecentSchemes() {
        Log.d(TAG, "Loading schemes added in last 24 hours");
        swipeRefreshLayout.setRefreshing(true);
        recentSchemeList.clear();

        final long twentyFourHoursAgo = System.currentTimeMillis() - TWENTY_FOUR_HOURS_MS;

        // Iterate through all categories
        schemesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String categoryName = categorySnapshot.getKey();
                    
                    for (DataSnapshot schemeSnapshot : categorySnapshot.getChildren()) {
                        try {
                            SchemeData schemeData = schemeSnapshot.getValue(SchemeData.class);
                            if (schemeData != null && schemeData.getCreatedAt() > twentyFourHoursAgo) {
                                recentSchemeList.add(new RecentSchemeItem(
                                        schemeSnapshot.getKey(),
                                        categoryName,
                                        schemeData
                                ));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing scheme: " + e.getMessage());
                        }
                    }
                }

                updateUI();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to load schemes: " + error.getMessage());
                Toast.makeText(AdminRecentSchemesActivity.this,
                        "Failed to load schemes: " + error.getMessage(),
                        Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateUI() {
        recentSchemeAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);

        if (recentSchemeList.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            recentSchemesRecyclerView.setVisibility(View.GONE);
            headerInfoText.setText("No schemes added in the last 24 hours");
        } else {
            emptyTextView.setVisibility(View.GONE);
            recentSchemesRecyclerView.setVisibility(View.VISIBLE);
            headerInfoText.setText(recentSchemeList.size() + " scheme(s) added in the last 24 hours");
        }
    }

    private void sendNotificationForScheme(RecentSchemeItem item) {
        Log.d(TAG, "Sending notification for scheme: " + item.schemeData.getScheme());
        // TODO: Implement Firebase Cloud Messaging to send notification
        Toast.makeText(this, 
                "Notification sent for: " + item.schemeData.getScheme(), 
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Data class for recent scheme items displayed in the list
     */
    private static class RecentSchemeItem {
        String schemeKey;
        String category;
        SchemeData schemeData;

        RecentSchemeItem(String schemeKey, String category, SchemeData schemeData) {
            this.schemeKey = schemeKey;
            this.category = category;
            this.schemeData = schemeData;
        }
    }

    /**
     * Adapter for displaying recent schemes in RecyclerView
     */
    private class RecentSchemeAdapter extends RecyclerView.Adapter<RecentSchemeAdapter.RecentSchemeViewHolder> {

        private final List<RecentSchemeItem> items;

        RecentSchemeAdapter(List<RecentSchemeItem> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public RecentSchemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recent_scheme, parent, false);
            return new RecentSchemeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecentSchemeViewHolder holder, int position) {
            RecentSchemeItem item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class RecentSchemeViewHolder extends RecyclerView.ViewHolder {
            TextView categoryBadge;
            TextView schemeNameText;
            TextView schemeDescriptionText;
            TextView notificationDateText;
            TextView createdAtText;
            Button viewUrlButton;
            Button sendNotificationButton;

            RecentSchemeViewHolder(@NonNull View itemView) {
                super(itemView);
                categoryBadge = itemView.findViewById(R.id.category_badge);
                schemeNameText = itemView.findViewById(R.id.scheme_name_text);
                schemeDescriptionText = itemView.findViewById(R.id.scheme_description_text);
                notificationDateText = itemView.findViewById(R.id.notification_date_text);
                createdAtText = itemView.findViewById(R.id.created_at_text);
                viewUrlButton = itemView.findViewById(R.id.view_url_button);
                sendNotificationButton = itemView.findViewById(R.id.send_notification_button);
            }

            void bind(RecentSchemeItem item) {
                categoryBadge.setText(item.category);
                schemeNameText.setText(item.schemeData.getScheme());
                schemeDescriptionText.setText(item.schemeData.getDescription());
                notificationDateText.setText("Notification: " + item.schemeData.getNotificationDate());

                // Format and display created at time
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
                String createdAtFormatted = sdf.format(new Date(item.schemeData.getCreatedAt()));
                createdAtText.setText("Added: " + createdAtFormatted);

                // Setup URL button
                if (item.schemeData.hasValidUrl()) {
                    viewUrlButton.setVisibility(View.VISIBLE);
                    viewUrlButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openUrl(item.schemeData.getUrl());
                        }
                    });
                } else {
                    viewUrlButton.setVisibility(View.GONE);
                }

                // Setup send notification button
                sendNotificationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendNotificationForScheme(item);
                    }
                });
            }

            private void openUrl(String url) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Toast.makeText(AdminRecentSchemesActivity.this,
                            "Could not open URL",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
