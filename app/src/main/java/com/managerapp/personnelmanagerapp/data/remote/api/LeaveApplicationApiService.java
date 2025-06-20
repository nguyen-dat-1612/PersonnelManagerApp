package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;

import java.util.List;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LeaveApplicationApiService {
    @GET("leave-applications/user/{userId}")
    Single<BaseResponse<List<LeaveApplicationResponse>>> getLeaveApplications(@Path("userId") long userId );

    @GET("leaveApplications/{id}")
    Single<BaseResponse<LeaveApplicationResponse>> getLeaveApplicationById(@Path("id") String id);

    @POST("leave-applications/create")
    Single<BaseResponse<LeaveApplicationResponse>> createLeaveApplication(@Body LeaveApplicationRequest leaveApplicationRequest);

    @GET("leave-applications")
    Single<BaseResponse<List<LeaveApplicationResponse>>> getApplicationIsPending(@Query("formStatusEnum") FormStatusEnum formStatusEnum, @Query("departmentId") String departmentId);

    @PUT("leave-applications/confirm/{applicationId}")
    Single<BaseResponse<LeaveApplicationResponse>> confirmApplication(
        @Path("applicationId") long applicationId,
        @Query("formStatusEnum") FormStatusEnum formStatusEnum
    );



}
