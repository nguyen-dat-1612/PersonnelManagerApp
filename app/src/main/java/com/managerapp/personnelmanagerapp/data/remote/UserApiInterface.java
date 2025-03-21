package com.managerapp.personnelmanagerapp.data.remote;

import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApiInterface {
    // Gọi api đăng nhập tài khoản người dùng
    @GET("users/user/{id}")
    Single<Response<UserResponse>> getUser(@Path("id") String id);

}
