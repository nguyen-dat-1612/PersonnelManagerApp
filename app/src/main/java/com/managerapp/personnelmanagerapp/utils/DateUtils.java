package com.managerapp.personnelmanagerapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

    // Lớp nội bộ để chứa ngày, tháng, năm
    public static class DateComponents {
        public int day;
        public int month; // Đã cộng 1 để khớp với tháng thông thường (1-12)
        public int year;

        public DateComponents(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    // Lấy ngày hiện tại dưới dạng DateComponents
    public static DateComponents getCurrentDateComponents() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1; // Cộng 1 vì tháng bắt đầu từ 0
        int year = calendar.get(Calendar.YEAR);
        return new DateComponents(day, month, year);
    }
    // Lấy ngày hiện tại dưới dạng String "dd/MM/yyyy"
    public static String getCurrentDateAsString() {
        DateComponents date = getCurrentDateComponents();
        return String.format("%02d/%02d/%04d", date.day, date.month, date.year);
    }

    // Định dạng ngày
    public static String formatDate(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    public static String convertToApiDateFormat(String inputDate) {
        try {
            SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat toApi = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return toApi.format(fromUser.parse(inputDate));
        } catch (Exception e) {
            return inputDate;
        }
    }
}
