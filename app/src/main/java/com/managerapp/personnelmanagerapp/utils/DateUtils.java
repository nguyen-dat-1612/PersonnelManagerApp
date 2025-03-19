package com.managerapp.personnelmanagerapp.utils;

import java.util.Calendar;

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

    // Định dạng ngày
    public static String formatDate(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }
}
