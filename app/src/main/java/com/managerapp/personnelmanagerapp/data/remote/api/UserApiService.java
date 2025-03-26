package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.RequestResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiService {

    // Lấy thông tin cá nhân theo userId
    @GET("user/info/{userId}")
    Single<Response<UserResponse>> getUser(@Path("userId") int userId);


    // Thay đổi mật khẩu
    @PUT("user/change-password/{userId}")
    Single<Response<RequestResponse>> changePassword(@Path("userId") int userId, @Body ChangePasswordRequest request);

}
