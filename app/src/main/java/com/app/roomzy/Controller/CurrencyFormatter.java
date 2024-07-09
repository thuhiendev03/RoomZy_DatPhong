package com.app.roomzy.Controller;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    public static String formatVietnameseCurrency(int amount) {
        // Sử dụng NumberFormat với Locale của Việt Nam
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        // Định dạng số và thêm ký tự 'đ' vào cuối
        return formatter.format(amount) + "đ";
    }
}
