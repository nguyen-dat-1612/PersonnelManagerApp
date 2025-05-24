package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DecisionApiService {
    @GET("decisions/{id}")
    Single<BaseResponse<DecisionResponse>> getDecisionById(@Path("id") String id);

    @GET("decisions/user/{userId}")
    Single<BaseResponse<List<DecisionResponse>>> getAllDecisionsByUserId(@Path("userId") Long userId);
}
