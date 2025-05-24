package com.managerapp.personnelmanagerapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    // Định dạng đầu vào kiểu ISO 8601 có mili giây: 2025-05-22T23:17:24.139452
    private static final SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());

    // Định dạng đầu ra mong muốn: dd/MM/yyyy HH:mm
    private static final SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));

    static {
        // Set timezone về GMT+7 cho Việt Nam (hoặc theo server, client nếu cần)
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
    }

    public static String formatSendDate(String sendDateStr) {
         if (sendDateStr == null || sendDateStr.trim().isEmpty()) {
            return "Không xác định"; //
        }
        try {
            Date date = inputFormat.parse(sendDateStr);
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sendDateStr;
    }
}
