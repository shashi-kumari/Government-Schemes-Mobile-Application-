package com.app.GovernmentSchemes;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provider class for fetching state scheme URLs from Firebase Realtime Database.
 * 
 * Expected database structure:
 * {
 *   "url": {
 *     "category": {
 *       "states": [
 *         {
 *           "code": "CA",
 *           "name": "California",
 *           "url": "https://example.com/ca"
 *         }
 *       ]
 *     }
 *   }
 * }
 */
public class StateSchemeProvider {
    private static final String TAG = "StateSchemeProvider";
    private static final String DATABASE_PATH = "url";
    
    // Thread-safe cache for storing fetched URLs to avoid repeated database calls
    private static final Map<String, Map<String, String>> urlCache = new ConcurrentHashMap<>();
    
    /**
     * Callback interface for async URL fetching operations.
     */
    public interface StateUrlCallback {
        void onUrlLoaded(@Nullable String url);
        void onError(String errorMessage);
    }
    
    /**
     * Callback interface for loading all URLs for a sector.
     */
    public interface SectorUrlsCallback {
        void onUrlsLoaded(Map<String, String> urls);
        void onError(String errorMessage);
    }
    
    /**
     * Fetches the URL for a specific state and sector from Firebase Database.
     * 
     * @param sector The scheme sector (e.g., AGRICULTURE, BANKING)
     * @param stateName The name of the state
     * @param callback Callback to receive the URL or error
     */
    public static void getStateUrl(SchemeSector sector, String stateName, StateUrlCallback callback) {
        String sectorName = sector.name().toLowerCase();
        
        // Check cache first
        if (urlCache.containsKey(sectorName)) {
            Map<String, String> sectorUrls = urlCache.get(sectorName);
            if (sectorUrls != null) {
                callback.onUrlLoaded(sectorUrls.get(stateName));
                return;
            }
        }
        
        // Fetch from database
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(DATABASE_PATH)
                .child(sectorName)
                .child("states");
        
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> sectorUrls = new ConcurrentHashMap<>();
                String foundUrl = null;
                
                for (DataSnapshot stateSnapshot : snapshot.getChildren()) {
                    StateUrlData stateData = stateSnapshot.getValue(StateUrlData.class);
                    if (stateData != null && stateData.getName() != null && stateData.getUrl() != null) {
                        sectorUrls.put(stateData.getName(), stateData.getUrl());
                        if (stateData.getName().equalsIgnoreCase(stateName)) {
                            foundUrl = stateData.getUrl();
                        }
                    }
                }
                
                // Update cache
                urlCache.put(sectorName, sectorUrls);
                
                callback.onUrlLoaded(foundUrl);
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
    }
    
    /**
     * Fetches all URLs for a specific sector from Firebase Database.
     * 
     * @param sector The scheme sector
     * @param callback Callback to receive the URLs map or error
     */
    public static void getSectorUrls(SchemeSector sector, SectorUrlsCallback callback) {
        String sectorName = sector.name().toLowerCase();
        
        // Check cache first
        if (urlCache.containsKey(sectorName)) {
            Map<String, String> cachedUrls = urlCache.get(sectorName);
            if (cachedUrls != null) {
                callback.onUrlsLoaded(new HashMap<>(cachedUrls));
                return;
            }
        }
        
        // Fetch from database
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(DATABASE_PATH)
                .child(sectorName)
                .child("states");
        
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> sectorUrls = new ConcurrentHashMap<>();
                
                for (DataSnapshot stateSnapshot : snapshot.getChildren()) {
                    StateUrlData stateData = stateSnapshot.getValue(StateUrlData.class);
                    if (stateData != null && stateData.getName() != null && stateData.getUrl() != null) {
                        sectorUrls.put(stateData.getName(), stateData.getUrl());
                    }
                }
                
                // Update cache
                urlCache.put(sectorName, sectorUrls);
                
                callback.onUrlsLoaded(new HashMap<>(sectorUrls));
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
    }
    
    /**
     * Clears the URL cache. Useful when data needs to be refreshed.
     */
    public static void clearCache() {
        urlCache.clear();
    }
    
    /**
     * Clears the cache for a specific sector.
     * 
     * @param sector The sector to clear from cache
     */
    public static void clearSectorCache(SchemeSector sector) {
        urlCache.remove(sector.name().toLowerCase());
    }
}
