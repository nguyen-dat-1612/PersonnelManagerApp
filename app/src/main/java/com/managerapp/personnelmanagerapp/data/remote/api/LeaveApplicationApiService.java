package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LeaveApplicationApiService {
    @GET("leave-applications/user/{userId}")
    Single<Response<BaseResponse<List<LeaveApplicationResponse>>>> getLeaveApplications(@Path("userId") int userId );

    @GET("leaveApplications/{id}")
    Single<Response<BaseResponse<LeaveApplication>>> getLeaveApplicationById(@Path("id") String id);

    @POST("leave-applications/create")
    Single<Response<BaseResponse<LeaveApplicationResponse>>> createLeaveApplication(@Body LeaveApplicationRequest leaveApplicationRequest);

    @GET("leave-applications")
    Single<Response<BaseResponse<List<LeaveApplicationResponse>>>> getApplicationIsPending(@Query("formStatusEnum") String formStatusEnum);

    @POST("leave-applications/confirm/{applicationId}")
    Single<Response<BaseResponse<LeaveApplicationResponse>>> confirmApplication(
        @Path("applicationId") long applicationId,
        @Query("formStatusEnum") String formStatusEnum
    );

}
