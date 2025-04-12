package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.DisciplineAssignment;
import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DisciplineAssignmentApiService {

    @GET("discipline-assignments/{id}")
    Single<Response<BaseResponse<List<AssignmentResponse>>>> getDisciplineAssignments(@Path("id") int userId);



    @GET("DisciplineAssignment/{userId}/{disciplineId}")
    Single<Response<BaseResponse<DisciplineAssignment>>> getDisciplineAssignmentById(
            @Path("userId") int userId,
            @Path("disciplineId") int disciplineId
    );
}
