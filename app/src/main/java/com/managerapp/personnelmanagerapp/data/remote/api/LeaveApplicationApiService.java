package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LeaveApplicationApiService {
    @GET("leaveApplications")
    Single<Response<LeaveApplicationResponse<List<LeaveApplication>>>> getLeaveApplications();

    @GET("leaveApplications/{id}")
    Single<Response<LeaveApplicationResponse<LeaveApplication>>> getLeaveApplicationById(@Path("id") String id);
}
