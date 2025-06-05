package com.managerapp.personnelmanagerapp.manager;

import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SecureTokenManager {
    private static final String TAG = "SecureTokenManager"; // Thêm TAG cho log
    private static final String KEY_ACCESS_TOKEN = "access_token";

    private final EncryptionManager encryptionManager;
    private final SharedPreferences sharedPreferences;

    @Inject
    public SecureTokenManager(EncryptionManager encryptionManager, SharedPreferences sharedPreferences) {
        this.encryptionManager = encryptionManager;
        this.sharedPreferences = sharedPreferences;
        Log.d(TAG, "SecureTokenManager initialized");
    }

    public void saveAccessToken(String token) {
        Log.d(TAG, "Original token: " + token); // Log token gốc

        String encryptedToken = encryptionManager.encrypt(token);
        Log.d(TAG, "Encrypted token: " + encryptedToken); // Log token đã mã hóa

        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, encryptedToken).apply();
        Log.d(TAG, "Token saved to SharedPreferences"); // Xác nhận đã lưu
    }

    public String getAccessToken() {
        String encryptedToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
        Log.d(TAG, "Retrieved encrypted token from SharedPreferences: " + encryptedToken); // Log token mã hóa từ SharedPreferences

        if (encryptedToken != null) {
            String decryptedToken = encryptionManager.decrypt(encryptedToken);
            Log.d(TAG, "Decrypted token: " + decryptedToken); // Log token đã giải mã
            return decryptedToken;
        } else {
            Log.d(TAG, "No token found in SharedPreferences");
            return null;
        }
    }

    public void clearTokens() {
        Log.d(TAG, "Clearing tokens from SharedPreferences");
        sharedPreferences.edit()
                .remove(KEY_ACCESS_TOKEN)
                .apply();
        Log.d(TAG, "Tokens cleared"); // Xác nhận đã xóa
    }
}