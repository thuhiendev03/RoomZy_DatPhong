package com.app.roomzy.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VoucherResponse {
    @SerializedName("vouchers")
    private ArrayList<Voucher> vouchers;

    public ArrayList<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(ArrayList<Voucher> vouchers) {
        this.vouchers = vouchers;
    }
}
