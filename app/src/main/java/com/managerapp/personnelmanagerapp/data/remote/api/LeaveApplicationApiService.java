package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LeaveApplicationApiService {
    @GET("leaveApplications")
    Single<Response<BaseResponse<List<LeaveApplication>>>> getLeaveApplications();

    @GET("leaveApplications/{id}")
    Single<Response<BaseResponse<LeaveApplication>>> getLeaveApplicationById(@Path("id") String id);
}
