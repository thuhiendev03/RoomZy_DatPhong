package com.app.roomzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.roomzy.Adapter.BookingHistoryAdapter;
import com.app.roomzy.Controller.ApiClient;
import com.app.roomzy.Controller.ApiService;
import com.app.roomzy.Models.Booking;
import com.app.roomzy.databinding.ActivityBookingHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryActivity extends AppCompatActivity {

    private ArrayList<Booking> bookingList = new ArrayList<>();
    private ActivityBookingHistoryBinding binding;
    private BookingHistoryAdapter mAdapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_history);
        mAuth = FirebaseAuth.getInstance();
        mAdapter = new BookingHistoryAdapter(this, bookingList, booking -> {
            Intent intent = new Intent(BookingHistoryActivity.this, BookingDetailActivity.class);
            intent.putExtra("booking_key",  booking);
            startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.bookingRecyclerView.setLayoutManager(layoutManager);
        binding.bookingRecyclerView.setAdapter(mAdapter);

        fetchBookingHistory();

    }

    private void fetchBookingHistory() {
        ApiService apiService = ApiClient.getApiService();
        String userId = mAuth.getUid();

        Call<ArrayList<Booking>> call = apiService.getBookingHistory(userId);
        call.enqueue(new Callback<ArrayList<Booking>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Booking>> call, @NonNull Response<ArrayList<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookingList.clear();
                    bookingList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();

                    if (bookingList.isEmpty()) {
                        binding.emptyBookingImage.setVisibility(View.VISIBLE);
                    } else {
                        binding.emptyBookingImage.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {
                Log.e("API Error", "Failed to fetch booking history", t);
                Toast.makeText(BookingHistoryActivity.this, "Failed to fetch booking history", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearBookingHistory() {

        bookingList.clear();
        mAdapter.notifyDataSetChanged();
        binding.emptyBookingImage.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Booking history cleared", Toast.LENGTH_SHORT).show();
    }
}