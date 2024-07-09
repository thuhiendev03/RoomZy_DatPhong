package com.app.roomzy.Adapter;//package com.app.roomzy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Models.Voucher;
import com.app.roomzy.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
        private List<Voucher> voucherList;
        private OnVoucherClickListener listener;

        public interface OnVoucherClickListener {
                void onVoucherClick(Voucher voucher);
        }

        public VoucherAdapter(List<Voucher> voucherList, OnVoucherClickListener listener) {
                this.voucherList = voucherList;
                this.listener = listener;
        }

        @NonNull
        @Override
        public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_voucher, parent, false);
                return new VoucherViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
                Voucher voucher = voucherList.get(position);
                holder.voucherName.setText(voucher.getTenVC());
                holder.voucherDescription.setText(voucher.getMoTa());
                holder.voucherDiscount.setText( voucher.getGiamGia());
                holder.voucherMaxDiscount.setText( CurrencyFormatter.formatVietnameseCurrency((int) voucher.getGiaToiDa()));
                Picasso.get().load(voucher.getHinh())
                        .into(holder.voucherImage);
                holder.itemView.setOnClickListener(v -> listener.onVoucherClick(voucher));

        }

        @Override
        public int getItemCount() {
                return voucherList.size();
        }

        public static class VoucherViewHolder extends RecyclerView.ViewHolder {
                TextView voucherName, voucherDescription, voucherDiscount, voucherMaxDiscount;
                ImageView voucherImage;

                public VoucherViewHolder(@NonNull View itemView) {
                        super(itemView);
                        voucherName = itemView.findViewById(R.id.voucherName);
                        voucherDescription = itemView.findViewById(R.id.voucherDescription);
                        voucherDiscount = itemView.findViewById(R.id.voucherDiscount);
                        voucherImage = itemView.findViewById(R.id.voucherImage);
                        voucherMaxDiscount = itemView.findViewById(R.id.voucherMaxDiscount);
                }
        }
}
