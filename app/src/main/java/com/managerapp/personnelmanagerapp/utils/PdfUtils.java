package com.managerapp.personnelmanagerapp.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.html2pdf.HtmlConverter;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PdfUtils {

    // Pdf liên quan đến báo cáo hợp đồng
    public static void exportPayrollToPdf(Context context, String dateRange, List<Payroll> data, String fileName) {
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
            for (Payroll item : data) {
                tableBody.append("<tr>")
                        .append("<td>").append(item.getUserId()).append("</td>")
                        .append("<td>").append(item.getFullName() != null ? item.getFullName() : "").append("</td>")
                        .append("<td>").append(CurrencyUtils.formatToVNDSimple(item.getSalary())).append("</td>")
                        .append("<td>").append(formatCurrency(item.getSeniority())).append("</td>")
                        .append("<td>").append(item.getWorkDays()).append("</td>")
                        .append("<td>").append(CurrencyUtils.doubleToInt(item.getActualWorkDays())).append("</td>")
                        .append("<td>").append(CurrencyUtils.doubleToInt(item.getUnpaidLeaveDays())).append("</td>")
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

    public static void exportContractExpireReport(Context context, String createdDate, int filterDays, List<ContractExpireReport> data, String fileName) {
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
            for (ContractExpireReport item : data) {
                tableBody.append("<tr>")
                        .append("<td>").append(item.getStt()).append("</td>")
                        .append("<td>").append(item.getFullName()).append("</td>")
                        .append("<td>").append(item.getEmail()).append("</td>")
                        .append("<td>").append(item.getDepartmentName()).append("</td>")
                        .append("<td>").append(item.getPositionName()).append("</td>")
                        .append("<td>").append(item.getContractTypeName()).append("</td>")
                        .append("<td>").append(DateUtils.convertToDayMonthYearFormat(item.getEndDate())).append("</td>")
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

            Log.d("FragmentDownloads", "Đã lưu vào: " + pdfFile.getAbsolutePath());
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


    // Pdf liên quan đến html webview:
    public static void saveBitmapToPdf(Context context, Bitmap bitmap, String fileName) {
        try {
            // Tạo tên file PDF
            String pdfName = fileName + "_" + System.currentTimeMillis() + ".pdf";

            // Lấy thư mục Download
            File downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS);
            if (!downloadsDir.exists()) {
                downloadsDir.mkdirs(); // Tạo nếu chưa có
            }

            File pdfFile = new File(downloadsDir, pdfName);

            // Tạo PDF
            PdfDocument pdfDocument = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            canvas.drawBitmap(bitmap, 0f, 0f, null);
            pdfDocument.finishPage(page);

            FileOutputStream fos = new FileOutputStream(pdfFile);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();

            // Quét media để cập nhật file mới (hiển thị trong các app khác)
            MediaScannerConnection.scanFile(context, new String[]{pdfFile.getAbsolutePath()}, null, null);

//            // Mở file PDF ngay lập tức
//            openPdfFile(context, pdfFile);
            Toast.makeText(context, "Đã tải xuống pdf thành công", Toast.LENGTH_SHORT).show();
            Log.d("PdfUtils", "Đã lưu vào: " + pdfFile.getAbsolutePath());
        } catch (IOException e) {
            Log.e("PdfUtils", "Lỗi khi lưu PDF: ", e);
            Toast.makeText(context, "Lỗi khi lưu file PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private static void openPdfFile(Context context, File pdfFile) {
        try {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PdfUtils", "Không thể mở file PDF", e);
            Toast.makeText(context, "Không thể mở file PDF", Toast.LENGTH_LONG).show();
        }
    }
}
