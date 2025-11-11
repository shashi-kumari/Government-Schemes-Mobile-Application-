package com.app.GovernmentSchemes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotificationListActivity extends BaseActivity {
    
    private ListView notificationListView;
    private List<Notification> notifications;
    private TextView header_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        
        // Setup common navigation elements
        setupCommonViews();
        
        header_title = findViewById(R.id.header_title);
        header_title.setText("Notifications");
        
        notificationListView = findViewById(R.id.notification_list_view);
        
        // Initialize notifications list
        notifications = new ArrayList<>();
        notifications.add(new Notification("Check your scheme", "https://www.myscheme.gov.in/search/user-journey"));
        
        // Create adapter for the list
        List<String> notificationTitles = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationTitles.add(notification.getTitle());
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_list_item_1,
            notificationTitles
        );
        
        notificationListView.setAdapter(adapter);
        
        // Set click listener for list items
        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notification = notifications.get(position);
                openUrl(notification.getUrl());
            }
        });
    }
    
    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
