package com.app.roomzy.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.roomzy.Models.CategoriesModel;
import com.app.roomzy.Models.ConFigManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesController {

    private final DatabaseReference databaseReference;

    public CategoriesController() {
        databaseReference = FirebaseDatabase.getInstance(ConFigManager.getFirebaseUrl()).getReference("categories");
    }

    public void getAllCategories(final OnCategoryDataLoadedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<CategoriesModel> categoryList = new ArrayList<>();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String id = categorySnapshot.child("Id").getValue(String.class);
                    String name = categorySnapshot.child("Name").getValue(String.class);
                    String imageUrl = categorySnapshot.child("Image").getValue(String.class);

                    CategoriesModel category = new CategoriesModel();
                    category.setId(id);
                    category.setImage(imageUrl);
                    category.setName(name);
                    categoryList.add(category);
                }
                listener.onCategoryDataLoaded(categoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CategoryController", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public interface OnCategoryDataLoadedListener {
        void onCategoryDataLoaded(ArrayList<CategoriesModel> categoryList);
    }
}
