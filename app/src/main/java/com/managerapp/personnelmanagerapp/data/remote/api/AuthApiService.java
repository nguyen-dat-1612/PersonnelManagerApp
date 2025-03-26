package com.managerapp.personnelmanagerapp.data.remote.api;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import com.managerapp.personnelmanagerapp.data.remote.request.TokenRefreshRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.TokenRefreshResponse;

public interface AuthApiService {

    // Gọi api đăng nhập tài khoản người dùng
    @Headers("No-Authentication: true")
    @POST("auth/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest request);

    @POST("refresh-token")
    Single<TokenRefreshResponse> refreshToken(@Body TokenRefreshRequest request);
}

