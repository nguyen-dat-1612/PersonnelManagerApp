package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.domain.model.JobGrade;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JobGradeApiService {
    @GET("job-grades")
    Single<BaseResponse<List<JobGradeResponse>>> getAllJobGrades();
    @GET("job-grades/user/{id}")
    Single<BaseResponse<JobGradeResponse>> getJobGradeById(@Path("id") Long id);
}
