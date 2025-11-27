package com.app.GovernmentSchemes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provider class for fetching scheme data from Firebase Database.
 * This class handles fetching schemes for different sectors and provides
 * caching to avoid repeated database calls.
 */
public class SchemeDataProvider {
    private static final String TAG = "SchemeDataProvider";
    private static final String DATABASE_PATH = "Scheme";
    
    // Thread-safe cache for storing fetched schemes to avoid repeated database calls
    private static final Map<String, List<SchemeData>> schemeCache = new ConcurrentHashMap<>();
    
    /**
     * Callback interface for async scheme fetching operations.
     */
    public interface SchemeListCallback {
        void onSchemesLoaded(List<SchemeData> schemes);
        void onError(String errorMessage);
    }
    
    /**
     * Fetches all schemes for a specific sector from Firebase Database.
     * 
     * @param sector The scheme sector (e.g., AGRICULTURE, BANKING)
     * @param callback Callback to receive the list of schemes or error
     */
    public static void getSchemesForSector(SchemeSector sector, SchemeListCallback callback) {
        String sectorName = sector.getDisplayName();
        
        // Check cache first
        if (schemeCache.containsKey(sectorName)) {
            List<SchemeData> cachedSchemes = schemeCache.get(sectorName);
            if (cachedSchemes != null) {
                callback.onSchemesLoaded(new ArrayList<>(cachedSchemes));
                return;
            }
        }
        
        // Fetch from database
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference(DATABASE_PATH)
                .child(sectorName);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SchemeData> schemes = new ArrayList<>();
                
                for (DataSnapshot schemeSnapshot : snapshot.getChildren()) {
                    try {
                        SchemeData schemeData = schemeSnapshot.getValue(SchemeData.class);
                        if (schemeData != null) {
                            schemes.add(schemeData);
                        } else {
                            Log.w(TAG, "Failed to deserialize scheme data for key: " + schemeSnapshot.getKey());
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error deserializing scheme data for key: " + schemeSnapshot.getKey(), e);
                    }
                }
                
                // Update cache
                schemeCache.put(sectorName, new ArrayList<>(schemes));
                
                callback.onSchemesLoaded(schemes);
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
    }
    
    /**
     * Clears the scheme cache. Useful when data needs to be refreshed.
     */
    public static void clearCache() {
        schemeCache.clear();
    }
    
    /**
     * Clears the cache for a specific sector.
     * 
     * @param sector The sector to clear from cache
     */
    public static void clearSectorCache(SchemeSector sector) {
        schemeCache.remove(sector.name().toLowerCase());
    }
}
