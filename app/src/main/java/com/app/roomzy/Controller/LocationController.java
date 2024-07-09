package com.app.roomzy.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.roomzy.Models.ConFigManager;
import com.app.roomzy.Models.LocationModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LocationController {
    private DatabaseReference databaseReference;

    public LocationController() {
        databaseReference = FirebaseDatabase.getInstance(ConFigManager.getFirebaseUrl()).getReference("locations");
    }

    public void getAllLocations(final OnLocationDataLoadedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<LocationModel> locationList = new ArrayList<>();
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String id = locationSnapshot.child("Id").getValue(String.class);
                    String name = locationSnapshot.child("Name").getValue(String.class);
                    String imageUrl = locationSnapshot.child("Image").getValue(String.class);

                    LocationModel location = new LocationModel(id, name, imageUrl);
                    locationList.add(location);
                }
                listener.onLocationDataLoaded(locationList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("LocationController", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public interface OnLocationDataLoadedListener {
        void onLocationDataLoaded(ArrayList<LocationModel> locationList);
    }
}
