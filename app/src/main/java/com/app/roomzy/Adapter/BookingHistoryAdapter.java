package com.app.roomzy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Models.Booking;
import com.app.roomzy.R;
import com.app.roomzy.databinding.LayoutItemBookingHistoryBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Booking> bookingList;
    private OnItemClickListener listener;

    public BookingHistoryAdapter(Context context, ArrayList<Booking> bookingList, OnItemClickListener listener) {
        this.context = context;
        this.bookingList = bookingList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Booking booking);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LayoutItemBookingHistoryBinding binding = LayoutItemBookingHistoryBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.binding.roomName.setText(booking.getRoomInfo().getName());
        holder.binding.roomPrice.setText(CurrencyFormatter.formatVietnameseCurrency((int) booking.getTotalPrice()));
        holder.binding.checkInDate.setText(booking.getCheckInDate());
        holder.binding.checkOutDate.setText(booking.getCheckOutDate());
        Picasso.get().load(booking.getRoomInfo().getImage()).into( holder.binding.roomImage);
        holder.binding.getRoot().setOnClickListener(v -> listener.onItemClick(booking));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutItemBookingHistoryBinding binding;

        public ViewHolder(@NonNull LayoutItemBookingHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}