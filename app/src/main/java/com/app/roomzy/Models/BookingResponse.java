package com.app.roomzy.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class  BookingResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("bookingId")
    private String bookingId;

    @SerializedName("roomId")
    private String roomId;

    @SerializedName("checkInDate")
    private String checkInDate;

    @SerializedName("checkOutDate")
    private String checkOutDate;

    @SerializedName("voucherId")
    private String voucherId;

    @SerializedName("voucherDetails")
    private VoucherDetails voucherDetails;

    @SerializedName("totalPrice")
    private long totalPrice;

    @SerializedName("finalPrice")
    private long finalPrice;

    @SerializedName("status")
    private String status;

    @SerializedName("bookingCode")
    private String bookingCode;

    @SerializedName("checkInCode")
    private String checkInCode;

    @SerializedName("roomInfo")
    private RoomInfo roomInfo;

    public BookingResponse(String message, String bookingId, String roomId, String checkInDate, String checkOutDate, String voucherId, VoucherDetails voucherDetails, long totalPrice, long finalPrice, String status, String bookingCode, String checkInCode, RoomInfo roomInfo) {
        this.message = message;
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.voucherId = voucherId;
        this.voucherDetails = voucherDetails;
        this.totalPrice = totalPrice;
        this.finalPrice = finalPrice;
        this.status = status;
        this.bookingCode = bookingCode;
        this.checkInCode = checkInCode;
        this.roomInfo = roomInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public VoucherDetails getVoucherDetails() {
        return voucherDetails;
    }

    public void setVoucherDetails(VoucherDetails voucherDetails) {
        this.voucherDetails = voucherDetails;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public String getCheckInCode() {
        return checkInCode;
    }

    public void setCheckInCode(String checkInCode) {
        this.checkInCode = checkInCode;
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    // Getters and setters for all fields

    public static class VoucherDetails {
        @SerializedName("discount")
        private String discount;

        @SerializedName("maxDiscountAmount")
        private String maxDiscountAmount;

        @SerializedName("description")
        private String description;

        public VoucherDetails(String discount, String maxDiscountAmount, String description) {
            this.discount = discount;
            this.maxDiscountAmount = maxDiscountAmount;
            this.description = description;
        }

        // Getters and setters for all fields
    }

    public static class RoomInfo {
        @SerializedName("address")
        private String address;

        @SerializedName("image")
        private String image;

        @SerializedName("subImages")
        private ArrayList<String> subImages;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        public RoomInfo(String address, String image, ArrayList<String> subImages, String name, String description) {
            this.address = address;
            this.image = image;
            this.subImages = subImages;
            this.name = name;
            this.description = description;
        }

        // Getters and setters for all fields
    }

    // Getters and setters for all fields
}
