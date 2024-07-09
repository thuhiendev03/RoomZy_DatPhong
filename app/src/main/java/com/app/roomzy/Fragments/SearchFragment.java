package com.app.roomzy.Fragments;

import static com.app.roomzy.FilterTheDataActivity.FILTER_CATEGORY;
import static com.app.roomzy.FilterTheDataActivity.FILTER_CATEGORY_NAME;
import static com.app.roomzy.FilterTheDataActivity.FILTER_LOCATION;
import static com.app.roomzy.FilterTheDataActivity.FILTER_LOCATION_NAME;
import static com.app.roomzy.FilterTheDataActivity.FILTER_MAX_PRICE;
import static com.app.roomzy.FilterTheDataActivity.FILTER_MIN_PRICE;
import static com.app.roomzy.FilterTheDataActivity.FILTER_SORT_ORDER;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roomzy.Adapter.AllCartAdapter;
import com.app.roomzy.Adapter.CartAdapter;
import com.app.roomzy.Adapter.CartAllItemAdapter;
import com.app.roomzy.Adapter.RecentHistoryAdapter;
import com.app.roomzy.Controller.CartDBManager;
import com.app.roomzy.Controller.FirebaseController;
import com.app.roomzy.Controller.HistoryDBManager;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Controller.RoomController;
import com.app.roomzy.FilterTheDataActivity;
import com.app.roomzy.MainActivity;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Objects;


public class SearchFragment extends Fragment implements HistoryUpdated {

    View mMainView;
    EditText searchBox;
    ImageView image_filter;
    ImageButton searchBtn;
    RecyclerView searchView;
    CartAllItemAdapter cartAdapter;
    ArrayList<Room> arrayList = new ArrayList<>();
    FirebaseController firebaseController;
    RoomController roomController;
    private static final int FILTER_REQUEST_CODE = 1;
    private boolean filterApplied = false;

    private String currentCategory;
    private String currentLocation;
    private String currentMinPrice;
    private String currentMaxPrice;
    private String currentLocationName;
    private String categoriesName;
    private  String sortOrder;


    TextView tvLocationName;

    HistoryDBManager productsHistory;
    RecentHistoryAdapter mRecentAdapter;
    public static HistoryUpdated historyUpdated;
    ArrayList<Room>recentHistory = new ArrayList<>();
    CartDBManager cartDBManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomController = new RoomController();
        historyUpdated = (HistoryUpdated) this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseController= new FirebaseController();
        mMainView = inflater.inflate(R.layout.fragment_search, container, false);
        searchBox = (EditText) mMainView.findViewById(R.id.search);
        searchBtn = (ImageButton) mMainView.findViewById(R.id.searchBtn);
        image_filter = (ImageView) mMainView.findViewById(R.id.image_filter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        searchView = (RecyclerView) mMainView.findViewById(R.id.searchView);
        searchView.setLayoutManager(linearLayoutManager);
        cartAdapter = new CartAllItemAdapter(getActivity(),arrayList ,getActivity().getSupportFragmentManager(), historyUpdated);
        searchView.setAdapter(cartAdapter);
        tvLocationName = (TextView) mMainView.findViewById(R.id.tvLocationName);
        search("");
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(searchBox.getText().toString());
                    return true;
                }
                return false;
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterApplied == false)
                    search(searchBox.getText().toString());
                else
                    searchWithFilters(searchBox.getText().toString(), currentCategory, currentLocation, currentMinPrice, currentMaxPrice, sortOrder);

            }
        });
        image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Mở Thành Công!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), FilterTheDataActivity.class);
                startActivityForResult(intent, FILTER_REQUEST_CODE);
            }
        });
        tvLocationName.setText("Tất cả");
        return mMainView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILTER_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            filterApplied = true;
            currentCategory = data.getStringExtra(FILTER_CATEGORY);
            currentLocation = data.getStringExtra(FILTER_LOCATION);
            currentMinPrice = data.getStringExtra(FILTER_MIN_PRICE);
            currentMaxPrice = data.getStringExtra(FILTER_MAX_PRICE);
            currentLocationName = data.getStringExtra(FILTER_LOCATION_NAME);
            sortOrder = data.getStringExtra(FILTER_SORT_ORDER);
            categoriesName = data.getStringExtra(FILTER_CATEGORY_NAME);
            tvLocationName.setText(currentLocationName + " - "+ categoriesName);


            applyFilters(currentCategory, currentLocation, currentMinPrice, currentMaxPrice, sortOrder);
        } else {
            filterApplied = false;
        }
    }
    private void applyFilters(String category, String location, String minPrice, String maxPrice, String sortOrder) {
        searchWithFilters(searchBox.getText().toString(), category, location, minPrice, maxPrice, sortOrder);
    }

    private void searchWithFilters(String phrase, String category, String location, String minPrice, String maxPrice, String sortOrder) {
        roomController.searchRoomsByFilters(phrase, category, location, minPrice, maxPrice, sortOrder, new RoomController.OnRoomDataLoadedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                arrayList.clear();
                arrayList.addAll(roomList);
                cartAdapter.notifyDataSetChanged();
            }
        });
    }
//    private void applyFilters(String category, String location, String minPrice, String maxPrice) {
//        searchWithFilters(searchBox.getText().toString(), category, location, minPrice, maxPrice);
//    }

    private void search(String phrase){
        roomController.searchRoomsByName(phrase, new RoomController.OnRoomDataLoadedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                arrayList.clear();
                arrayList.addAll(roomList);
                cartAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getUpdateResult(boolean isUpdated) {
        if (isUpdated){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recentHistory.clear();
                    recentHistory.addAll(productsHistory.getAllViewedRooms());
                }
            },1000);
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}