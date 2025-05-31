package com.managerapp.personnelmanagerapp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.html2pdf.HtmlConverter;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PdfUtils {

    public static void exportPayrollToPdf(Context context, String dateRange, List<PayrollResponse> data, String fileName) {
        try {
            // 1. Đọc file HTML template
            InputStream inputStream = context.getAssets().open("payroll_monthly.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder htmlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                htmlBuilder.append(line).append("\n");
            }
            String htmlTemplate = htmlBuilder.toString();

            // 2. Tạo nội dung bảng lương
            StringBuilder tableBody = new StringBuilder();
            for (PayrollResponse item : data) {
                tableBody.append("<tr>")
                        .append("<td>").append(item.getUserId()).append("</td>")
                        .append("<td>").append(item.getFullName() != null ? item.getFullName() : "").append("</td>")
                        .append("<td>").append(formatCurrency(item.getSalary())).append("</td>")
                        .append("<td>").append(formatCurrency(item.getSeniority())).append("</td>")
                        .append("<td>").append(item.getWorkDays()).append("</td>")
                        .append("<td>").append(item.getActualWorkDays()).append("</td>")
                        .append("<td>").append(item.getUnpaidLeaveDays()).append("</td>")
                        .append("</tr>");
            }

            // 3. Chèn dữ liệu vào template
            String filledHtml = htmlTemplate
                    .replace("{{DATE_RANGE}}", dateRange != null ? dateRange : "")
                    .replace("{{TABLE_BODY}}", tableBody.toString());

            // 4. Tạo file PDF
            File pdfFile = new File(context.getExternalFilesDir(null), fileName + ".pdf");
            FileOutputStream outputStream = new FileOutputStream(pdfFile);
            HtmlConverter.convertToPdf(filledHtml, outputStream);

            // 5. Mở PDF
            openPdfWithIntent(context, pdfFile);

        } catch (Exception e) {
            Log.e("PdfUtils", "Lỗi tạo PDF", e);
            Toast.makeText(context, "Lỗi tạo PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static void exportContractExpireReport(Context context, String createdDate, int filterDays, List<ContractExpireReportResponse> data, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open("contract_expiring_report.html");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder htmlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                htmlBuilder.append(line).append("\n");
            }
            String htmlTemplate = htmlBuilder.toString();

            StringBuilder tableBody = new StringBuilder();
            for (ContractExpireReportResponse item : data) {
                tableBody.append("<tr>")
                        .append("<td>").append(item.getStt()).append("</td>")
                        .append("<td>").append(item.getFullName()).append("</td>")
                        .append("<td>").append(item.getEmail()).append("</td>")
                        .append("<td>").append(item.getDepartmentName()).append("</td>")
                        .append("<td>").append(item.getPositionName()).append("</td>")
                        .append("<td>").append(item.getContractTypeName()).append("</td>")
                        .append("<td>").append(item.getEndDate()).append("</td>")
                        .append("<td>").append(item.getRemainingDays()).append("</td>")
                        .append("<td>").append(item.getContractStatus()).append("</td>")
                        .append("</tr>");
            }

            String filledHtml = htmlTemplate
                    .replace("{{CREATED_DATE}}", createdDate)
                    .replace("{{FILTER_DAYS}}", String.valueOf(filterDays))
                    .replace("{{TABLE_BODY}}", tableBody.toString());

            File pdfFile = new File(context.getExternalFilesDir(null), fileName + ".pdf");
            FileOutputStream outputStream = new FileOutputStream(pdfFile);
            HtmlConverter.convertToPdf(filledHtml, outputStream);

            openPdfWithIntent(context, pdfFile);

        } catch (Exception e) {
            Log.e("PdfUtils", "Lỗi tạo PDF", e);
            Toast.makeText(context, "Lỗi tạo PDF: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static void openPdfWithIntent(Context context, File file) {
        try {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PdfUtils", e.getMessage(), e);
            Toast.makeText(context, "Không thể mở file PDF", Toast.LENGTH_LONG).show();
        }
    }

    private static String formatCurrency(double value) {
        return String.format("%,.0f", value);
    }

    public static void downloadFile(Context context, String fileUrl, String fileName) {
        try {
            String fileNameUrl = "https://www.googleapis.com/drive/v3/files/"+fileUrl+"?fields=name&key=AIzaSyB3sNzITjsRHLvRq68dECM-w8N2tS0ZznQ";

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileNameUrl));
            request.setTitle(fileName);
            request.setDescription("Đang tải " + fileName);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            request.setMimeType("application/pdf");

            // Lấy DownloadManager và bắt đầu tải
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);

            Toast.makeText(context, "Bắt đầu tải " + fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("DownloadPDF", "Lỗi khi tải file: " + e.getMessage());
            Toast.makeText(context, "Lỗi khi tải file", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setupDownloadButton(Context context, ImageView downloadButton, String fileUrl, String fileName) {
        downloadButton.setVisibility(fileUrl != null && !fileUrl.isEmpty() ? View.VISIBLE : View.GONE);
        downloadButton.setOnClickListener(v -> downloadFile(context, fileUrl, fileName));
    }
}
