package com.managerapp.personnelmanagerapp.data.remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
public interface AuthApiService {

    // Gọi api đăng nhập tài khoản người dùng
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}

