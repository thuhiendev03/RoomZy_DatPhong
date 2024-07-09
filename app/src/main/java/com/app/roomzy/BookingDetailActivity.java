package com.app.roomzy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roomzy.Controller.ApiClient;
import com.app.roomzy.Controller.ApiService;
import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Models.Booking;
import com.app.roomzy.Models.Room;
import com.app.roomzy.databinding.ActivityBookingHistoryBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDetailActivity extends AppCompatActivity {
    ActivityBookingHistoryBinding binding;
    ImageView bannerImage;
    TextView hotelName;
    TextView roomPrice, txtCheckout, txtCheckin, txtSoNgay,
            txtGiamGia, txtTotalPayment, txtRoomType, txtBedType,
            txtFacilities,txtCheckinCode;
    Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        addControl();

//        Booking booking = (Booking) getIntent().getSerializableExtra("booking");
        Intent intent = getIntent();
        Booking booking = intent.getParcelableExtra("booking_key");
        if (booking != null) {
            setBooking(booking);
        }

        btnCancel.setOnClickListener(View -> cancelBooking());
    }
    private void addControl() {
        bannerImage = findViewById(R.id.bannerImage);
        hotelName = findViewById(R.id.hotelName);
        roomPrice= findViewById(R.id.roomPrice);
        txtCheckout = findViewById(R.id.txtCheckout);
        txtCheckin = findViewById(R.id.txtCheckin);
        txtGiamGia = findViewById(R.id.txtGiamGia);
        txtSoNgay = findViewById(R.id.txtSoNgay);
        txtTotalPayment = findViewById(R.id.txtTotalPayment);
        txtRoomType = findViewById(R.id.txtRoomType);
        txtFacilities = findViewById(R.id.txtFacilities);
        txtCheckinCode = findViewById(R.id.txtCheckinCode);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(View -> ShowPayment());
    }

    void ShowPayment()
    {
        Intent intent = new Intent(this, PaymentActivity.class);
        startActivity(intent);

    }
    private long daysBetween(Calendar startDate, Calendar endDate) {
        long startTime = startDate.getTimeInMillis();
        long endTime = endDate.getTimeInMillis();
        return (endTime - startTime) / (1000 * 60 * 60 * 24);
    }
    private static Calendar stringToCalendar(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        date = sdf.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    private void setBooking(Booking booking)  {
        Picasso.get().load(booking.getRoomInfo().getImage()).into(bannerImage);
        hotelName.setText(booking.getRoomInfo().getName());
        roomPrice.setText(booking.getStatus());
        txtCheckout.setText(booking.getCheckOutDate());
        txtGiamGia.setText(CurrencyFormatter.formatVietnameseCurrency((int) (booking.getTotalPrice() - booking.getFinalPrice())));
        txtTotalPayment.setText(CurrencyFormatter.formatVietnameseCurrency((int) booking.getFinalPrice()));
        txtRoomType.setText(booking.getRoomInfo().getAddress());
        txtFacilities.setText(booking.getRoomInfo().getDescription());
        txtCheckout.setText(booking.getCheckOutDate());

        txtCheckinCode.setText(booking.getCheckInCode());
        try {
            // Chuyển đổi chuỗi ngày thành đối tượng Calendar
            Calendar startDate = stringToCalendar(booking.getCheckInDate());
            Calendar endDate = stringToCalendar(booking.getCheckOutDate());
            long diffInDays = daysBetween(startDate, endDate);
            txtSoNgay.setText(String.valueOf(diffInDays));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void cancelBooking() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận hủy phòng");
        builder.setMessage("Bạn có chắt chắn muốn hủy booking không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked Yes, proceed with cancellation
                performCancellation();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked No, do nothing
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void performCancellation() {
        ApiService apiService = ApiClient.getApiService();
        Intent intent = getIntent();
        Booking booking = intent.getParcelableExtra("booking_key");
        String bookingId = booking.getBookingId(); // Assuming you have a method to get booking ID

        Call<Void> call = apiService.cancelBooking(bookingId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookingDetailActivity.this, "Booking canceled successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingDetailActivity.this, BookingHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorResponse = response.errorBody().string();
                            JSONObject jsonObject = new JSONObject(errorResponse);
                            String errorMessage = jsonObject.getString("error");
                            Toast.makeText(BookingDetailActivity.this, "Failed to cancel booking " + errorMessage, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookingDetailActivity.this, "Failed to cancel booking " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(BookingDetailActivity.this, "Failed to cancel booking " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle network errors
                Toast.makeText(BookingDetailActivity.this, "Network error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}