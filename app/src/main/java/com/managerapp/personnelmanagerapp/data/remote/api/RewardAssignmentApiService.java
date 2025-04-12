package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RewardAssignmentApiService {

    @GET("reward-assignments/{userId}")
    Single<Response<BaseResponse<List<AssignmentResponse>>>> getRewardAssignments(@Path("userId") int userId);

    @GET("RewardAssignment/{userId}/{rewardId}")
    Single<Response<BaseResponse<AssignmentResponse>>> getRewardAssignmentById(
            @Path("userId") int userId,
            @Path("rewardId") int rewardId
    );
}
