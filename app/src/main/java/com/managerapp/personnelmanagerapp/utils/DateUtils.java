package com.managerapp.personnelmanagerapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static String convertToDayMonthYearFormat(String inputDate) {
        try {
            // Tạo định dạng đầu vào (nhận cả yyyy-M-d và yyyy-MM-dd)
            SimpleDateFormat fromInput = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
            Date date = fromInput.parse(inputDate);

            // Tạo định dạng đầu ra với đầy đủ 2 chữ số cho ngày và tháng
            SimpleDateFormat toOutput = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            return toOutput.format(date);
        } catch (Exception e) {
            return inputDate; // Trả về nguyên bản nếu có lỗi
        }
    }

    /**
     * Chuyển đổi từ DateComponents sang định dạng "dd-MM-yyyy"
     *
     * @param dateComponents Đối tượng DateComponents
     * @return Chuỗi ngày tháng định dạng "dd-MM-yyyy"
     */
    public static String formatDateToDayMonthYear(DateComponents dateComponents) {
        return String.format("%02d-%02d-%04d",
                dateComponents.day,
                dateComponents.month,
                dateComponents.year);
    }
}
