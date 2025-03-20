package com.managerapp.personnelmanagerapp.data.remote;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.RequestResponse;

public interface AuthApiService {

    // Gọi api đăng nhập tài khoản người dùng
    @POST("auth/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest request);

}

