package com.managerapp.personnelmanagerapp.data.repository;

import com.google.gson.Gson;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.FeedbackResponse;
import com.managerapp.personnelmanagerapp.domain.repository.FeedBackRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

public class FeedBackRepositoryImpl implements FeedBackRepository {

    private static final String TAG = "FeedBackRepository";
    private final FeedbackApiService feedbackApiService;
    private final Gson gson = new Gson();

    @Inject
    public FeedBackRepositoryImpl(FeedbackApiService feedbackApiService) {
        this.feedbackApiService = feedbackApiService;
    }

    public Single<FeedbackResponse> sendFeedback(FeedbackRequest request) {
        return RxResultHandler.handle(feedbackApiService.sendFeedback(request));
    }
}
