package com.app.roomzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.app.roomzy.Adapter.AllCartAdapter;
import com.app.roomzy.Adapter.CartAllItemAdapter;
import com.app.roomzy.Adapter.CartColumnAdapter;
import com.app.roomzy.Controller.RoomController;
import com.app.roomzy.Models.Room;
import com.app.roomzy.databinding.ActivityViewAllBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Objects;

public class ViewAllActivity extends AppCompatActivity {

    String type;
    int limit =10;
//    DocumentSnapshot lastVisible;
    ActivityViewAllBinding binding;
    private boolean isScrolling = false;
    ArrayList<Room> products = new ArrayList<>();
    ArrayList<Room> locals = new ArrayList<>();
    ArrayList<Room> categoryList = new ArrayList<>();
    private boolean isLastItemReached = false;
//    View productAdapter;
//    ViewAllProductGridADapter gridADapter;
    CartColumnAdapter cartColumnAdapter;
    CartAllItemAdapter cartAllItemAdapter;
    RoomController roomController;
    String locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        roomController = new RoomController();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_all);

        type = getIntent().getStringExtra("type");
        if (type != null && type.equalsIgnoreCase("location")) {
            locationId = getIntent().getStringExtra("locationId");
            String  locationName = getIntent().getStringExtra("locationName");
            binding.title.setText(locationName);
            getRoomsByLocation(locationId);
        } else {
            getCategoryProducts("all");
        }
        cartColumnAdapter = new CartColumnAdapter(products,this,getSupportFragmentManager());
        binding.allProductsView.setAdapter(cartColumnAdapter);
        binding.allProductsView.setLayoutManager(new GridLayoutManager(this, 3));
    }
    private void getCategoryProducts(String category) {
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                products.clear();
                products.addAll(roomList);
                Log.e("PhamNguyen", categoryList.toString());
                cartColumnAdapter.notifyDataSetChanged();
            }
        });

    }
    private void getRoomsByLocation(String locationId) {
        roomController.getRoomsByLocation(locationId, new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                products.clear();
                products.addAll(roomList);
                cartColumnAdapter.notifyDataSetChanged();
            }
        });
    }
}