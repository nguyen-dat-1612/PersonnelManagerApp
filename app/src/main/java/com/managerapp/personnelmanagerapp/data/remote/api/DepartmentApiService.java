package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.DepartmentResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DepartmentApiService {
    @GET("departments")
    Single<BaseResponse<List<DepartmentResponse>>> getAllDepartments();

    @GET("departments/user/{id}")
    Single<BaseResponse<DepartmentResponse>> getDepartmentByUserId(@Path("id") long id);
}
