package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.local.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.TokenRefreshRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.TokenRefreshResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class AuthRepository {
    private final AuthApiService authApiService;
    private final SecureTokenManager secureTokenManager;

    @Inject
    public AuthRepository(AuthApiService authApiService, SecureTokenManager secureTokenManager) {
        this.authApiService = authApiService;
        this.secureTokenManager = secureTokenManager;
    }

    public Single<LoginResponse> login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        return authApiService.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("Auth Repository", "Đăng nhập thành công");
//                        secureTokenManager.saveAccessToken(response.accessToken);
//                        secureTokenManager.saveRefreshToken(response.refreshToken);
//                        secureTokenManager.setTokenExpiry(response.expiryTime);
                        return Single.just(response.body());
                    } else {
                        String errorMessage = "Đăng nhập thất bại với mã lỗi: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e("Auth Repository", "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d("Auth Repository", "Đăng nhập thất bại: " + email + "." + password + ": " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<TokenRefreshResponse> refreshToken() {
        String refreshToken = secureTokenManager.getRefreshToken();
        if (refreshToken == null) {
            return Single.error(new Exception("No refresh token"));
        }

        TokenRefreshRequest request = new TokenRefreshRequest(refreshToken);
        return authApiService.refreshToken(request)
                .doOnSuccess(response -> {
                    secureTokenManager.saveAccessToken(response.getAccessToken());
                    secureTokenManager.setTokenExpiry(response.getExpiresIn());
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
