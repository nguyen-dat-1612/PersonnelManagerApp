package com.managerapp.personnelmanagerapp.data.remote.api;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;

public interface AuthApiService {
    @Headers("No-Authentication: true")
    @POST("auth/login")
    Single<BaseResponse<String>> login(@Body LoginRequest request);

    @Headers("No-Authentication: true")
    @GET("auth/forgotPassword")
    Single<BaseResponse> forgotPassword(@Query("email") String email);

    @Headers("No-Authentication: true")
    @GET("auth/verifyOTP")
    Single<BaseResponse<Boolean>> verifyOTP(@Query("email") String email, @Query("otp") String otp);

    @Headers("No-Authentication: true")
    @POST("auth/resetPassword")
    Single<BaseResponse<String>> resetPassword(@Query("newPass") String newPass, @Query("email") String email);

    @POST("auth/logout")
    Single<BaseResponse<Void>> logout(@Body String token);

    @GET("auth/role")
    Single<BaseResponse<String>> getRole(@Query("token") String token);
}

