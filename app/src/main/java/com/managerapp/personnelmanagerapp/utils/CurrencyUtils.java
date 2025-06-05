package com.managerapp.personnelmanagerapp.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyUtils {

    public static int doubleToInt(double value) {
        return (int) Math.round(value);
    }

    /**
     * Chuyển đổi số double sang định dạng tiền VND
     * Ví dụ: 1200000.0 -> "1.200.000 ₫"
     *
     * @param amount Số tiền cần chuyển đổi
     * @return Chuỗi định dạng tiền VND
     */
    public static String formatToVND(double amount) {
        // Tạo DecimalFormat với pattern của Việt Nam
        DecimalFormat vndFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());

        // Thiết lập ký hiệu tiền tệ là ₫
        symbols.setCurrencySymbol("₫");
        // Dấu phân cách hàng nghìn là dấu chấm
        symbols.setGroupingSeparator('.');
        // Dấu phân cách thập phân là dấu phẩy
        symbols.setDecimalSeparator(',');

        vndFormat.setDecimalFormatSymbols(symbols);
        // Pattern định dạng: #,###.00 ¤ (¤ là ký hiệu tiền tệ)
        vndFormat.applyPattern("#,##0.00 ¤");

        // Format số tiền và loại bỏ phần thập phân nếu là .00
        String formatted = vndFormat.format(amount);
        return formatted.replace(",00", ""); // Bỏ phần thập phân nếu không cần thiết
    }

    /**
     * Chuyển đổi số double sang định dạng tiền VND (đơn giản)
     * Ví dụ: 1200000.0 -> "1.200.000 VND"
     *
     * @param amount Số tiền cần chuyển đổi
     * @return Chuỗi định dạng tiền VND đơn giản
     */
    public static String formatToVNDSimple(double amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VND";
    }
}
