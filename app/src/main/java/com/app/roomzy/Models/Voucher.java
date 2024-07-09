package com.app.roomzy.Models;

import com.google.gson.annotations.SerializedName;

public class Voucher {
    @SerializedName("voucherId")
    private String MaVC;

    @SerializedName("name")
    private String TenVC;

    @SerializedName("description")
    private String MoTa;

    @SerializedName("discount")
    private String GiamGia;

    @SerializedName("maxDiscountAmount")
    private double GiaToiDa;

    @SerializedName("image")
    private String Hinh;

    @SerializedName("receivedDate")
    private String receivedDate;

    @SerializedName("status")
    private String status;

        public Voucher(){

        }
        public Voucher(String tenVC, String moTa, String giamGia, String hinh) {
            this.TenVC = tenVC;
            this.MoTa = moTa;
            this.GiamGia = giamGia;
            this.Hinh = hinh;
        }

    public Voucher(String maVC, String tenVC, String moTa, String giamGia, double giaToiDa, String hinh, String receivedDate, String status) {
        MaVC = maVC;
        TenVC = tenVC;
        MoTa = moTa;
        GiamGia = giamGia;
        GiaToiDa = giaToiDa;
        Hinh = hinh;
        this.receivedDate = receivedDate;
        this.status = status;
    }

    public String getTenVC() {
            return TenVC;
        }

        public void setTenVC(String tenVC) {
            TenVC = tenVC;
        }

        public String getMoTa() {
            return MoTa;
        }

        public void setMoTa(String moTa) {
            MoTa = moTa;
        }

        public String getHinh() {
            return Hinh;
        }

        public void setHinh(String hinh) {
            Hinh = hinh;
        }

        public String getGiamGia() {
            return GiamGia;
        }

        public void setGiamGia(String giamGia) {
            GiamGia = giamGia;
        }

        public BookingRequest getMaVC() {
            return null;
        }


     public String getMaVCString() {
             return  MaVC;
        }
        public void setMaVC(String maVC) {
            MaVC = maVC;
        }


        public double getGiaToiDa() {
            return GiaToiDa;
        }

        public void setGiaToiDa(double giaToiDa) {
            GiaToiDa = giaToiDa;
        }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


