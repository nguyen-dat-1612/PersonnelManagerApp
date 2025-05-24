package com.managerapp.personnelmanagerapp.utils;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.managerapp.personnelmanagerapp.utils.manager.SecureTokenManager;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

// Tách interceptor thành một class riêng
public class AuthInterceptor implements Interceptor {
    private final SecureTokenManager secureTokenManager;
    private final Context context;

    @Inject
    public AuthInterceptor(SecureTokenManager secureTokenManager, Context context) {
        this.secureTokenManager = secureTokenManager;
        this.context = context;
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

        Response response = chain.proceed(authenticatedRequest);
        // Nếu token hết hạn => đá người dùng về login
        if (response.code() == 401) {
            secureTokenManager.clearTokens(); // Xoá token
            Intent intent = new Intent("force_logout");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }

        return response;
    }
}
