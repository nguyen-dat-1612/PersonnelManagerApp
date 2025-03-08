package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.domain.model.UserModel;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {
    private final AuthApiService authApiService;

    public AuthRepository(AuthApiService authApiService) {
        this.authApiService = authApiService;
    }

    public void login(String email, String password, AuthCallback callback) {
        LoginRequest request = new LoginRequest(email, password);
        authApiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                    Log.d("Auth Repository", "Đăng nhập thành công");
                } else {
                    String errorMessage;
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string(); // Lấy chi tiết lỗi từ server
                        } else {
                            errorMessage = "Đăng nhập thất bại với mã lỗi: " + response.code();
                        }
                    } catch (IOException e) {
                        errorMessage = "Không thể đọc lỗi từ phản hồi.";
                    }
                    callback.onFailure(errorMessage);
                    Log.d("Auth Repository", "Đăng nhập thất bại ."+email + "." + password+": " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String errorMsg = "Lỗi kết nối: " + t.getMessage();
                callback.onFailure(errorMsg);
                Log.e("Auth Repository", errorMsg);
            }
        });
    }

    public interface AuthCallback {
        void onSuccess(LoginResponse response);
        void onFailure(String error);
    }

}
