package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionApproveRequest;
import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.internal.operators.observable.ObservableError;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DecisionApiService {
    @POST("decisions/create")
    Single<BaseResponse<DecisionResponse>> createDecision(@Body DecisionRequest decisionRequest);

    @GET("decisions/{id}")
    Single<BaseResponse<DecisionResponse>> getDecisionById(@Path("id") String id);

    @GET("decisions/user/{userId}")
    Single<BaseResponse<List<DecisionResponse>>> getAllDecisionsByUserId(@Path("userId") Long userId);

    @GET("decisions")
    Observable<BaseResponse<List<DecisionResponse>>> getAllDecisions(@Query("type") String type);

    @PUT("decisions/{id}")
    Single<BaseResponse<DecisionResponse>> updateDecision(@Path("id") String id, @Body DecisionApproveRequest decisionApproveRequest);

    @DELETE("decisions/{id}")
    Maybe<BaseResponse<Void>> deleteDecision(@Path("id") String id);
}
