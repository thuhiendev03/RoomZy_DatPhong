package com.app.roomzy.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Booking implements Parcelable {
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
    private double totalPrice;

    @SerializedName("finalPrice")
    private double finalPrice;

    @SerializedName("status")
    private String status;

    @SerializedName("bookingCode")
    private String bookingCode;

    @SerializedName("checkInCode")
    private String checkInCode;

    @SerializedName("roomInfo")
    private RoomInfo roomInfo;

    // Constructor
    public Booking() {
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
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

    protected Booking(Parcel in) {
        bookingId = in.readString();
        roomId = in.readString();
        checkInDate = in.readString();
        checkOutDate = in.readString();
        voucherId = in.readString();
        voucherDetails = in.readParcelable(VoucherDetails.class.getClassLoader());
        totalPrice = in.readDouble();
        finalPrice = in.readDouble();
        status = in.readString();
        bookingCode = in.readString();
        checkInCode = in.readString();
        roomInfo = in.readParcelable(RoomInfo.class.getClassLoader());
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookingId);
        parcel.writeString(roomId);
        parcel.writeString(checkInDate);
        parcel.writeString(checkOutDate);
        parcel.writeString(voucherId);
        parcel.writeParcelable(voucherDetails, i);
        parcel.writeDouble(totalPrice);
        parcel.writeDouble(finalPrice);
        parcel.writeString(status);
        parcel.writeString(bookingCode);
        parcel.writeString(checkInCode);
        parcel.writeParcelable(roomInfo, i);
    }

    // Getters and setters...

    public static class VoucherDetails implements Parcelable {
        @SerializedName("discount")
        private String discount;

        @SerializedName("maxDiscountAmount")
        private double maxDiscountAmount;

        @SerializedName("description")
        private String description;

        // Constructor
        public VoucherDetails() {
        }

        protected VoucherDetails(Parcel in) {
            discount = in.readString();
            maxDiscountAmount = in.readDouble();
            description = in.readString();
        }

        public static final Creator<VoucherDetails> CREATOR = new Creator<VoucherDetails>() {
            @Override
            public VoucherDetails createFromParcel(Parcel in) {
                return new VoucherDetails(in);
            }

            @Override
            public VoucherDetails[] newArray(int size) {
                return new VoucherDetails[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(discount);
            parcel.writeDouble(maxDiscountAmount);
            parcel.writeString(description);
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public double getMaxDiscountAmount() {
            return maxDiscountAmount;
        }

        public void setMaxDiscountAmount(double maxDiscountAmount) {
            this.maxDiscountAmount = maxDiscountAmount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Getters and setters...
    }

    public static class RoomInfo implements Parcelable {
        @SerializedName("address")
        private String address;

        @SerializedName("image")
        private String image;

        @SerializedName("subImages")
        private List<String> subImages;

        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        // Constructor
        public RoomInfo() {
        }

        protected RoomInfo(Parcel in) {
            address = in.readString();
            image = in.readString();
            subImages = in.createStringArrayList();
            name = in.readString();
            description = in.readString();
        }

        public static final Creator<RoomInfo> CREATOR = new Creator<RoomInfo>() {
            @Override
            public RoomInfo createFromParcel(Parcel in) {
                return new RoomInfo(in);
            }

            @Override
            public RoomInfo[] newArray(int size) {
                return new RoomInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(address);
            parcel.writeString(image);
            parcel.writeStringList(subImages);
            parcel.writeString(name);
            parcel.writeString(description);
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<String> getSubImages() {
            return subImages;
        }

        public void setSubImages(List<String> subImages) {
            this.subImages = subImages;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        // Getters and setters...
    }

    // Getters and setters...
}