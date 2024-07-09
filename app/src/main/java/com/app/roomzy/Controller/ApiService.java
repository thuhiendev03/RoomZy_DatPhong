package com.app.roomzy.Controller;


import com.app.roomzy.Models.Booking;
import com.app.roomzy.Models.BookingRequest;
import com.app.roomzy.Models.BookingResponse;
import com.app.roomzy.Models.ConnectionResponse;
import com.app.roomzy.Models.Voucher;
import com.app.roomzy.Models.VoucherResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/booking-history")
    Call<ArrayList<Booking>> getBookingHistory(@Query("userId") String userId);

    @GET("/user-vouchers")
    Call<VoucherResponse> getUserVouchers(@Query("userId") String userId);
    @GET("/check-connection")
    Call<ConnectionResponse> checkConnection();

    @POST("/book-room")
    Call<BookingResponse> bookRoom(@Body BookingRequest bookingRequest);

    @GET("/booking-details")
    Call<Booking> bookRoomDetail(@Query("bookingId") String bookingId);

    @DELETE("/cancel-booking")
    Call<Void> cancelBooking(@Query("bookingId") String bookingId);
}
