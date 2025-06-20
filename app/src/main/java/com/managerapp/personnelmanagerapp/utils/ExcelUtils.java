package com.managerapp.personnelmanagerapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelUtils {

    /**
     * Tạo Workbook Excel cho bảng lương nhân viên (chỉ tạo dữ liệu, chưa lưu file).
     */
    public static Workbook createPayrollWorkbook(List<Payroll> payrollList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo cáo lương");

        // Tạo header
        Row headerRow = sheet.createRow(0);
        String[] headers = {
                "Mã nhân viên", "Nhân viên", "Lương cơ bản", "Lương thâm niên",
                "Số ngày làm việc", "Số ngày làm thực tế", "Nghỉ không lương"
        };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            // Đặt độ rộng cột cố định (tránh lỗi autoSizeColumn trên Android)
            sheet.setColumnWidth(i, 20 * 256);
        }

        // Tạo dữ liệu
        for (int i = 0; i < payrollList.size(); i++) {
            Payroll p = payrollList.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(p.getUserId());
            row.createCell(1).setCellValue(p.getFullName());
            row.createCell(2).setCellValue(p.getSalary());
            row.createCell(3).setCellValue(p.getSeniority());
            row.createCell(4).setCellValue(p.getWorkDays());
            row.createCell(5).setCellValue(p.getActualWorkDays());
            row.createCell(6).setCellValue(p.getUnpaidLeaveDays());
        }

        return workbook;
    }

    /**
     * Tạo Workbook Excel cho bảng báo cáo nhân viên gần hết hạn (chỉ tạo dữ liệu, ).
     */

    public static Workbook createContractExpireWorkbook(List<ContractExpireReport> contractExpire) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo cáo hợp đồng sắp hết hạn");


        // Tạo header
        Row headerRow = sheet.createRow(0);
        String[] headers = {
                "STT", "Họ tên", "Email", "Phòng ban", "Chức vụ", "Loại HĐ", "Ngày hết hạn", "Còn lại (ngày)", "Trạng thái"
        };
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            // Đặt độ rộng cột cố định (tránh lỗi autoSizeColumn trên Android)
            sheet.setColumnWidth(i, 20 * 256);
        }

        // Tạo dữ liệu
        for (int i = 0; i < contractExpire.size(); i++) {
            ContractExpireReport c = contractExpire.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(c.getStt());
            row.createCell(1).setCellValue(c.getFullName());
            row.createCell(2).setCellValue(c.getEmail());
            row.createCell(3).setCellValue(c.getDepartmentName());
            row.createCell(4).setCellValue(c.getPositionName());
            row.createCell(5).setCellValue(c.getContractTypeName());
            row.createCell(6).setCellValue(c.getEndDate());
            row.createCell(7).setCellValue(c.getRemainingDays());
            row.createCell(8).setCellValue(c.getContractStatus().getStringRes());
        }

        return workbook;

    }


    /**
     * Lưu Workbook Excel vào file trong thư mục Downloads.
     * @param context Context
     * @param workbook Workbook cần lưu
     * @param fileName Tên file (ví dụ: "bang_luong.xlsx")
     * @return Uri file nếu lưu thành công, hoặc null nếu lỗi
     */
    public static Uri saveWorkbookToDownloads(Context context, Workbook workbook, String fileName) {
        Uri fileUri = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10 trở lên
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                values.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                values.put(MediaStore.Downloads.IS_PENDING, 1);

                Uri collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                fileUri = context.getContentResolver().insert(collection, values);

                if (fileUri != null) {
                    try (OutputStream out = context.getContentResolver().openOutputStream(fileUri)) {
                        workbook.write(out);
                    }

                    values.clear();
                    values.put(MediaStore.Downloads.IS_PENDING, 0);
                    context.getContentResolver().update(fileUri, values, null, null);
                } else {
                    throw new IOException("Không thể tạo file trong thư mục Downloads");
                }
            } else {
                // Android 9 trở xuống: ghi trực tiếp
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(downloadsDir, fileName);
                try (OutputStream out = new FileOutputStream(file)) {
                    workbook.write(out);
                }
                fileUri = Uri.fromFile(file);
            }

            workbook.close();
            return fileUri;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
