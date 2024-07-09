package com.app.roomzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.roomzy.Adapter.CartAdapter;
import com.app.roomzy.Controller.CartDBManager;
import com.app.roomzy.Models.Room;
import com.app.roomzy.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<Room> arrayList = new ArrayList<>();
    ActivityCartBinding binding;
    CartAdapter mAdapter;
    CartDBManager cartDBManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartDBManager = new CartDBManager(this);
        arrayList = cartDBManager.getAllCartRooms();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);

        mAdapter = new CartAdapter(this,arrayList,cartDBManager,getSupportFragmentManager());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.cartView.setLayoutManager(layoutManager);
        binding.cartView.setAdapter(mAdapter);


        mAdapter.notifyDataSetChanged();
        Log.e("size", String.valueOf(arrayList.size()));

        if (arrayList.size() == 0)
            binding.emptyCart.setVisibility(View.VISIBLE);
        else
            binding.emptyCart.setVisibility(View.GONE);

    }
}