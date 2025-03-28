package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RewardAssignmentApiService {

    @GET("RewardAssignment/{id}")
    Single<Response<BaseResponse<List<RewardAssignment>>>> getRewardAssignments(@Path("id") int userId);

    @GET("RewardAssignment/{userId}/{rewardId}")
    Single<Response<BaseResponse<RewardAssignment>>> getRewardAssignmentById(
            @Path("userId") int userId,
            @Path("rewardId") int rewardId
    );
}
