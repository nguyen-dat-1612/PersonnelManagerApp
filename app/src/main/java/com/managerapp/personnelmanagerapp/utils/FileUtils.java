package com.managerapp.personnelmanagerapp.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    private static final String TAG = "FileUtils";

    public static String loadAssetTextAsString(Context context, String fileName) {
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            Log.e(TAG, "Lỗi khi đọc file từ assets: " + e.getMessage());
            return null;
        }
    }
}
