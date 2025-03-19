package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthApiService authApiService;

    public AuthRepository(AuthApiService authApiService) {
        this.authApiService = authApiService;
    }
    public Single<LoginResponse> login(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        return authApiService.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d("Auth Repository", "Đăng nhập thành công");
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
}
