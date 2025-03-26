package com.managerapp.personnelmanagerapp.data.local;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
// SecureTokenManager: Quản lý tokens và các hoạt động bảo mật
public class SecureTokenManager {
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_TOKEN_EXPIRY = "token_expiry";

    private final EncryptionManager encryptionManager;
    private final SharedPreferences sharedPreferences;

    @Inject
    public SecureTokenManager(EncryptionManager encryptionManager, SharedPreferences sharedPreferences) {
        this.encryptionManager = encryptionManager;
        this.sharedPreferences = sharedPreferences;
    }

    public void saveAccessToken(String token) {
        String encryptedToken = encryptionManager.encrypt(token);
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, encryptedToken).apply();
    }

    public String getAccessToken() {
        String encryptedToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
        return encryptedToken != null ? encryptionManager.decrypt(encryptedToken) : null;
    }

    public void saveRefreshToken(String token) {
        String encryptedToken = encryptionManager.encrypt(token);
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, encryptedToken).apply();
    }

    public String getRefreshToken() {
        String encryptedToken = sharedPreferences.getString(KEY_REFRESH_TOKEN, null);
        return encryptedToken != null ? encryptionManager.decrypt(encryptedToken) : null;
    }

    public boolean isTokenExpired() {
        long expiryTime = sharedPreferences.getLong(KEY_TOKEN_EXPIRY, 0);
        return System.currentTimeMillis() > expiryTime;
    }

    public void clearTokens() {
        sharedPreferences.edit()
                .remove(KEY_ACCESS_TOKEN)
                .remove(KEY_REFRESH_TOKEN)
                .remove(KEY_TOKEN_EXPIRY)
                .apply();
    }

    public void setTokenExpiry(long expiryTimeMillis) {
        sharedPreferences.edit().putLong(KEY_TOKEN_EXPIRY, expiryTimeMillis).apply();
    }
}