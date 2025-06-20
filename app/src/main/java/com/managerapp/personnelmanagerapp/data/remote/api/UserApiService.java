package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.ChangePasswordRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserSummaryResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.domain.model.User;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApiService {

    @GET("users/info")
    Single<BaseResponse<UserProfileResponse>> getUser();

    // Thay đổi mật khẩu
    @POST("users/change-pass")
    Maybe<BaseResponse<Boolean>> changePassword( @Body ChangePasswordRequest request);

    // Lấy quá trình công tác
    @GET("users/work-log")
    Single<BaseResponse<List<WorkLogResponse>>> getWorkLog(@Query("userId") long userId);

    // Lưu FCM của thiết bị
    @PUT("users/save-device/{userId}")
    Single<BaseResponse<Boolean>> saveDevice(@Path("userId") long userId, @Query("deviceToken") String token);

    // Tìm kiếm người dùng
    @GET("users/search")
    Observable<BaseResponse<List<UserSummaryResponse>>> searchUser(@Query("keyword") String query);

    @PUT("users/remove-device/{id}")
    Maybe<BaseResponse> removeDevice(@Path("id") long id);


}
