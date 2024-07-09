package com.app.roomzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.roomzy.Adapter.CartAdapter;
import com.app.roomzy.Adapter.RecentHistoryAdapter;
import com.app.roomzy.Controller.CartDBManager;
import com.app.roomzy.Controller.DatabaseHelper;
import com.app.roomzy.Controller.HistoryDBManager;
import com.app.roomzy.Models.Room;
import com.app.roomzy.databinding.ActivityCartBinding;
import com.app.roomzy.databinding.ActivityHistoryBinding;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ArrayList<Room> arrayList = new ArrayList<>();
    ActivityHistoryBinding binding;
    RecentHistoryAdapter mAdapter;
    HistoryDBManager historyDBManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyDBManager = new HistoryDBManager(this);
        arrayList = historyDBManager.getAllViewedRooms();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_history);

        mAdapter = new RecentHistoryAdapter(this,arrayList,historyDBManager,getSupportFragmentManager());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.cartView.setLayoutManager(layoutManager);
        binding.cartView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        Log.e("size", String.valueOf(arrayList.size()));
        binding.clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                historyDBManager.deleteRow();
                arrayList.clear();
                binding.emptyCart.setVisibility(View.VISIBLE);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Product view history cleared", Toast.LENGTH_SHORT).show();
            }
        });
        if (arrayList.size() == 0)
            binding.emptyCart.setVisibility(View.VISIBLE);
        else
            binding.emptyCart.setVisibility(View.GONE);
    }
}