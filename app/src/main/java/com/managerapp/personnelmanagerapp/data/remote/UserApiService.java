package com.managerapp.personnelmanagerapp.data.remote;

import com.managerapp.personnelmanagerapp.data.remote.request.LoginRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LoginResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.RequestResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserInfoResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiService {

    // Lấy thông tin cá nhân theo userId
    @GET("user/info/{userId}")
    Single<Response<UserInfoResponse>> getUserInfo(@Path("userId") String userId);


    // Xóa tài khoản user
    @DELETE("user/delete/{userId}")
    Single<Response<RequestResponse>> deleteUser(@Path("userId") String userId);


    // Thay đổi mật khẩu
    @PUT("user/change-password/{userId}")
    Single<Response<RequestResponse>> changePassword(@Path("userId") String userId, @Header("acess_token") String Token, @Body ChangePasswordRequest request);

}
