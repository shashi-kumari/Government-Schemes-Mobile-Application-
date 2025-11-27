package com.app.GovernmentSchemes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Admin User List Activity
 * Displays a list of all users for admin to view, modify, or delete.
 */
public class AdminUserListActivity extends AppCompatActivity {

    private static final String TAG = "AdminUserListActivity";

    private RecyclerView usersRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyTextView;
    private ImageView backButton;
    
    private UserAdapter userAdapter;
    private List<HelperClass> userList;
    private DatabaseReference usersReference;
    private String adminUuid;
    private ThemeManager themeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize theme manager and apply current theme
        themeManager = new ThemeManager(this);
        themeManager.applyCurrentTheme();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        // Get admin UUID from intent
        adminUuid = getIntent().getStringExtra("adminUuid");

        // Initialize Firebase reference
        usersReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize views
        usersRecyclerView = findViewById(R.id.users_recycler_view);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        emptyTextView = findViewById(R.id.empty_text_view);
        backButton = findViewById(R.id.back_button);

        // Set up RecyclerView
        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersRecyclerView.setAdapter(userAdapter);

        // Set up swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadUsers();
            }
        });

        // Set up back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Load users
        loadUsers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when returning from detail activity
        loadUsers();
    }

    private void loadUsers() {
        Log.d(TAG, "Loading users from database");
        swipeRefreshLayout.setRefreshing(true);

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    HelperClass user = userSnapshot.getValue(HelperClass.class);
                    if (user != null) {
                        user.setUuid(userSnapshot.getKey());
                        userList.add(user);
                    }
                }

                Log.d(TAG, "Loaded " + userList.size() + " users");
                
                userAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

                // Show empty state if no users
                if (userList.isEmpty()) {
                    emptyTextView.setVisibility(View.VISIBLE);
                    usersRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                    usersRecyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to load users: " + error.getMessage());
                Toast.makeText(AdminUserListActivity.this, 
                    "Failed to load users: " + error.getMessage(), 
                    Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Adapter for displaying users in RecyclerView
     */
    private class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
        
        private List<HelperClass> users;

        UserAdapter(List<HelperClass> users) {
            this.users = users;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            HelperClass user = users.get(position);
            holder.bind(user);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class UserViewHolder extends RecyclerView.ViewHolder {
            TextView nameTextView;
            TextView emailTextView;
            TextView usernameTextView;
            TextView adminBadge;

            UserViewHolder(@NonNull View itemView) {
                super(itemView);
                nameTextView = itemView.findViewById(R.id.user_name_text);
                emailTextView = itemView.findViewById(R.id.user_email_text);
                usernameTextView = itemView.findViewById(R.id.user_username_text);
                adminBadge = itemView.findViewById(R.id.admin_badge);
            }

            void bind(HelperClass user) {
                nameTextView.setText(user.getName() != null ? user.getName() : "N/A");
                emailTextView.setText(user.getEmail() != null ? user.getEmail() : "N/A");
                usernameTextView.setText("@" + (user.getUsername() != null ? user.getUsername() : "N/A"));
                
                // Show admin badge if user is admin
                if (user.getAdmnAccess()) {
                    adminBadge.setVisibility(View.VISIBLE);
                } else {
                    adminBadge.setVisibility(View.GONE);
                }

                // Set click listener to open user details
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailIntent = new Intent(AdminUserListActivity.this, 
                                AdminUserDetailActivity.class);
                        detailIntent.putExtra("userUuid", user.getUuid());
                        detailIntent.putExtra("adminUuid", adminUuid);
                        startActivity(detailIntent);
                    }
                });
            }
        }
    }
}
