package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DecisionApiService {
    @GET("decisions")
    Single<Response<BaseResponse<List<DecisionResponse>>>> getDecisions(@Path("userId") long userId);
}
