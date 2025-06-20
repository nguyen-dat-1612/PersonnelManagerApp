package com.managerapp.personnelmanagerapp.network;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.managerapp.personnelmanagerapp.manager.SecureTokenManager;
import java.io.IOException;
import javax.inject.Inject;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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

        if (originalRequest.header("No-Authentication") != null) {
            return chain.proceed(originalRequest);
        }
        Request authenticatedRequest = token != null
                ? originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
                : originalRequest;

        Response response = chain.proceed(authenticatedRequest);

        if (response.code() == 401) {
            secureTokenManager.clearTokens();
            Intent intent = new Intent("force_logout");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }

        return response;
    }
}
