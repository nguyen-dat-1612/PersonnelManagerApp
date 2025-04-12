package com.managerapp.personnelmanagerapp.data.remote.api;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import com.managerapp.personnelmanagerapp.data.remote.request.TokenRefreshRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.TokenRefreshResponse;

public interface AuthApiService {

    @Headers("No-Authentication: true")
    @POST("auth/login")
    Single<Response<LoginResponse>> login(@Body LoginRequest request);

    @POST("auth/refresh-token")
    Single<Response<TokenRefreshResponse>> refreshToken(@Body TokenRefreshRequest request);

    @Headers("No-Authentication: true")
    @GET("auth/forgotPassword")
    Single<Response<BaseResponse>> forgotPassword(@Query("email") String email);

    @Headers("No-Authentication: true")
    @GET("auth/verifyOTP")
    Single<Response<BaseResponse<Boolean>>> verifyOTP(@Query("email") String email, @Query("otp") String otp);

    @Headers("No-Authentication: true")
    @POST("auth/resetPassword")
    Single<Response<BaseResponse<String>>> resetPassword(@Query("newPass") String newPass, @Query("email") String email);

    @POST("auth/logout")
    Single<Response<BaseResponse<Void>>> logout(@Body String token);
}

