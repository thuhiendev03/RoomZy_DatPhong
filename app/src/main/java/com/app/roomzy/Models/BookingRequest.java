package com.app.roomzy.Models;

import com.google.gson.annotations.SerializedName;

public class  BookingRequest {
    @SerializedName("userId")
    private String userId;

    @SerializedName("roomId")
    private String roomId;

    @SerializedName("checkInDate")
    private String checkInDate;

    @SerializedName("checkOutDate")
    private String checkOutDate;

    @SerializedName("voucherId")
    private String voucherId;

    @SerializedName("totalPrice")
    private long totalPrice;
    public BookingRequest()
    {
    }
    public BookingRequest(String userId, String roomId, String checkInDate, String checkOutDate, String voucherId, long totalPrice) {
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.voucherId = voucherId;
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }
}