package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FeedbackApiService {
    @POST("feedback")
    Single<Response<BaseResponse<String>>> sendFeedback(@Body FeedbackRequest feedbackRequest);
}
