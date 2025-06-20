package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveTypeResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface LeaveTypeApiService {
    @GET("leave-types")
    Single<BaseResponse<List<LeaveTypeResponse>>> getAllLeaveTypes();
}
