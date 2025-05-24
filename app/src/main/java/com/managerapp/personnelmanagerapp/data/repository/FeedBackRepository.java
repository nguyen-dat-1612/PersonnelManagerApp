package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.FeedbackResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

@Singleton
public class FeedBackRepository {

    private static final String TAG = "FeedBackRepository";
    private final FeedbackApiService feedbackApiService;
    private final Gson gson = new Gson();

    @Inject
    public FeedBackRepository(FeedbackApiService feedbackApiService) {
        this.feedbackApiService = feedbackApiService;
    }

    public Single<FeedbackResponse> sendFeedback(FeedbackRequest request) {
        return RxResultHandler.handle(feedbackApiService.sendFeedback(request));
    }
}
