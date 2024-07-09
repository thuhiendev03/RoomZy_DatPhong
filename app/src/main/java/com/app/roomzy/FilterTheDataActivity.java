package com.app.roomzy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.app.roomzy.Adapter.CategoriesAdapter;
import com.app.roomzy.Adapter.CategoryAdapter;
import com.app.roomzy.Adapter.LocationAdapter;
import com.app.roomzy.Controller.CategoriesController;
import com.app.roomzy.Controller.LocationController;
import com.app.roomzy.Models.CategoriesModel;
import com.app.roomzy.Models.LocationModel;

import java.util.ArrayList;

public class FilterTheDataActivity extends AppCompatActivity {

    public static final String FILTER_CATEGORY = "FILTER_CATEGORY";
    public static final String FILTER_LOCATION = "FILTER_LOCATION";
    public static final String FILTER_LOCATION_NAME = "FILTER_LOCATION_NAME";
    public static final String FILTER_CATEGORY_NAME = "FILTER_CATEGORY_NAME";
    public static final String FILTER_MIN_PRICE = "FILTER_MIN_PRICE";
    public static final String FILTER_MAX_PRICE = "FILTER_MAX_PRICE";
    public static final String FILTER_IS_APPLY = "FILTER_IS_APPLY";
    public static final String FILTER_SORT_ORDER = "FILTER_SORT_ORDER";


    private Spinner categorySpinner;
    private Spinner locationSpinner;

    private CategoryAdapter categoryAdapter;
    private LocationAdapter locationAdapter;


    ArrayList<LocationModel> locationModelArrayList;
    ArrayList<CategoriesModel> categoriesModelArrayList;
    EditText minPrice, maxPrice;
    RadioButton sortAscending, sortDescending, sortNew;
    Button applyButton, clearBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_the_data);
        categorySpinner = findViewById(R.id.categorySpinner);
        locationSpinner = findViewById(R.id.locationSpinner);
        minPrice = findViewById(R.id.minPrice);
        maxPrice = findViewById(R.id.maxPrice);
        sortAscending = findViewById(R.id.sortAscending);
        sortDescending = findViewById(R.id.sortDescending);
        sortNew = findViewById(R.id.sortNew);
        applyButton = findViewById(R.id.applyFiltersButton);
        clearBtn = findViewById(R.id.clearFiltersButton);
        locationModelArrayList = new ArrayList<>();
        categoriesModelArrayList = new ArrayList<>();

        loadLocations();
        loadCategories();
        loadFilterOptions();
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilters();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFilters();
            }
        });
    }

    private void loadCategories() {
        CategoriesController categoryController = new CategoriesController();
        categoryController.getAllCategories(new CategoriesController.OnCategoryDataLoadedListener() {
            @Override
            public void onCategoryDataLoaded(ArrayList<CategoriesModel> categoryList) {
                categoryAdapter = new CategoryAdapter(FilterTheDataActivity.this, categoryList);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(categoryAdapter);
                categoriesModelArrayList.addAll(categoryList);
            }
        });
    }

    private void loadLocations() {
        LocationController locationController = new LocationController();
        locationController.getAllLocations(new LocationController.OnLocationDataLoadedListener() {
            @Override
            public void onLocationDataLoaded(ArrayList<LocationModel> locationList) {
                locationAdapter = new LocationAdapter(FilterTheDataActivity.this, locationList);
                locationAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                locationSpinner.setAdapter(locationAdapter);
                locationModelArrayList.addAll(locationList);
            }
        });
    }
    private void applyFilters() {
//        int selectedCategory = categorySpinner.getSelectedItemPosition();
//        int selectedLocation = locationSpinner.getSelectedItemPosition();
//        String selectedMinPrice = minPrice.getText().toString();
//        String selectedMaxPrice = maxPrice.getText().toString();
//        CategoriesModel categoriesModel = categoriesModelArrayList.get(selectedCategory);
//        LocationModel locationModel = locationModelArrayList.get(selectedLocation);
//
//        saveFilterOptions(categoriesModel.getId(), locationModel.getId(), selectedMinPrice, selectedMaxPrice, locationModel.getName(), categoriesModel.getName());
//
//        Intent intent = new Intent();
//        intent.putExtra(FILTER_CATEGORY, categoriesModel.getId());
//        intent.putExtra(FILTER_LOCATION, locationModel.getId());
//        intent.putExtra(FILTER_MIN_PRICE, selectedMinPrice);
//        intent.putExtra(FILTER_MAX_PRICE, selectedMaxPrice);
//        intent.putExtra(FILTER_LOCATION_NAME, locationModel.getName());
//
//        setResult(RESULT_OK, intent);
//        finish();
        int selectedCategory = categorySpinner.getSelectedItemPosition();
        int selectedLocation = locationSpinner.getSelectedItemPosition();
        String selectedMinPrice = minPrice.getText().toString();
        String selectedMaxPrice = maxPrice.getText().toString();
        CategoriesModel categoriesModel = categoriesModelArrayList.get(selectedCategory);
        LocationModel locationModel = locationModelArrayList.get(selectedLocation);

        String sortOrder = sortNew.isChecked() ? "newest" :
                sortAscending.isChecked() ? "ascending" : "descending";

        saveFilterOptions(categoriesModel.getId(), locationModel.getId(), selectedMinPrice, selectedMaxPrice, locationModel.getName(), categoriesModel.getName(), sortOrder);

        Intent intent = new Intent();
        intent.putExtra(FILTER_CATEGORY, categoriesModel.getId());
        intent.putExtra(FILTER_LOCATION, locationModel.getId());
        intent.putExtra(FILTER_MIN_PRICE, selectedMinPrice);
        intent.putExtra(FILTER_MAX_PRICE, selectedMaxPrice);
        intent.putExtra(FILTER_LOCATION_NAME, locationModel.getName());
        intent.putExtra(FILTER_CATEGORY_NAME, categoriesModel.getName());

        intent.putExtra(FILTER_SORT_ORDER, sortOrder);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void clearFilters() {
        categorySpinner.setSelection(0);
        locationSpinner.setSelection(0);
        minPrice.setText("");
        maxPrice.setText("");
        sortNew.setChecked(true);

        SharedPreferences sharedPreferences = getSharedPreferences("FilterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

//    private void saveFilterOptions(String category, String location, String minPrice, String maxPrice, String locationName, String categoryName) {
//        SharedPreferences sharedPreferences = getSharedPreferences("FilterPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(FILTER_CATEGORY, category);
//        editor.putString(FILTER_LOCATION, location);
//        editor.putString(FILTER_MIN_PRICE, minPrice);
//        editor.putString(FILTER_MAX_PRICE, maxPrice);
//        editor.putString(FILTER_LOCATION_NAME, locationName);
//        editor.putString(FILTER_CATEGORY_NAME, categoryName);
//        editor.apply();
//    }
    private void saveFilterOptions(String category, String location, String minPrice, String maxPrice, String locationName, String categoryName, String sortOrder) {
        SharedPreferences sharedPreferences = getSharedPreferences("FilterPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FILTER_CATEGORY, category);
        editor.putString(FILTER_LOCATION, location);
        editor.putString(FILTER_MIN_PRICE, minPrice);
        editor.putString(FILTER_MAX_PRICE, maxPrice);
        editor.putString(FILTER_LOCATION_NAME, locationName);
        editor.putString(FILTER_CATEGORY_NAME, categoryName);
        editor.putString(FILTER_SORT_ORDER, sortOrder);
        editor.apply();
    }

    private void loadFilterOptions() {
        SharedPreferences sharedPreferences = getSharedPreferences("FilterPrefs", MODE_PRIVATE);
        String category = sharedPreferences.getString(FILTER_CATEGORY, "All");
        String location = sharedPreferences.getString(FILTER_LOCATION, "All");
        String minPrice = sharedPreferences.getString(FILTER_MIN_PRICE, "");
        String maxPrice = sharedPreferences.getString(FILTER_MAX_PRICE, "");
        String sortOrder = sharedPreferences.getString(FILTER_SORT_ORDER, "newest");

        setSpinnerSelection(categorySpinner, category);
        setSpinnerSelection(locationSpinner, location);
        this.minPrice.setText(minPrice);
        this.maxPrice.setText(maxPrice);

        if (sortOrder.equals("newest")) {
            sortNew.setChecked(true);
        } else if (sortOrder.equals("ascending")) {
            sortAscending.setChecked(true);
        } else if (sortOrder.equals("descending")) {
            sortDescending.setChecked(true);
        }
    }

    private void setSpinnerSelection(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
//    private void loadFilterOptions() {
//        SharedPreferences sharedPreferences = getSharedPreferences("FilterPrefs", MODE_PRIVATE);
//        String category = sharedPreferences.getString(FILTER_CATEGORY, "All");
//        String location = sharedPreferences.getString(FILTER_LOCATION, "All");
//        String minPrice = sharedPreferences.getString(FILTER_MIN_PRICE, "");
//        String maxPrice = sharedPreferences.getString(FILTER_MAX_PRICE, "");
//
//        setSpinnerSelection(categorySpinner, category);
//        setSpinnerSelection(locationSpinner, location);
//        this.minPrice.setText(minPrice);
//        this.maxPrice.setText(maxPrice);
//    }
//
//    private void setSpinnerSelection(Spinner spinner, String value) {
//        for (int i = 0; i < spinner.getCount(); i++) {
//            if (spinner.getItemAtPosition(i).toString().equals(value)) {
//                spinner.setSelection(i);
//                break;
//            }
//        }
//    }


}