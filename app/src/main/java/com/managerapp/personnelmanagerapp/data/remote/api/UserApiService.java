package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.domain.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    // Lấy thông tin cá nhân theo userId
    @GET("users/info")
    Single<Response<BaseResponse<UserProfileResponse>>> getUser();

    // Thay đổi mật khẩu
    @POST("users/change-pass")
    Single<Response<BaseResponse>> changePassword( @Body ChangePasswordRequest request);

    // Lấy quá trình công tác
    @GET("users/work-log")
    Single<Response<BaseResponse<List<WorkLogResponse>>>> getWorkLog(@Query("userId") long userId);
}
