package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.User;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApiService {

    // Lấy thông tin cá nhân theo userId
    @GET("users/info")
    Single<Response<BaseResponse<User>>> getUser();


    // Thay đổi mật khẩu
    @POST("users/change-pass")
    Single<Response<BaseResponse>> changePassword( @Body ChangePasswordRequest request);

}
