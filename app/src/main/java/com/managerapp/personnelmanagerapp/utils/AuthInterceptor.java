package com.managerapp.personnelmanagerapp.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

// Tách interceptor thành một class riêng
public class AuthInterceptor implements Interceptor {
    private final SecureTokenManager secureTokenManager;

    public AuthInterceptor(SecureTokenManager secureTokenManager) {
        this.secureTokenManager = secureTokenManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = secureTokenManager.getAccessToken();

        // Nếu có header "No-Authentication", bỏ qua việc gắn token
        if (originalRequest.header("No-Authentication") != null) {
            return chain.proceed(originalRequest);
        }
        Request authenticatedRequest = token != null
                ? originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
                : originalRequest;

        return chain.proceed(authenticatedRequest);
    }
}
