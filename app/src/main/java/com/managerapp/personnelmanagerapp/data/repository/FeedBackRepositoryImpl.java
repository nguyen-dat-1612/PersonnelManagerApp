package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.FeedbackResponse;
import com.managerapp.personnelmanagerapp.domain.repository.FeedBackRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class FeedBackRepositoryImpl implements FeedBackRepository {

    private final FeedbackApiService feedbackApiService;
    private final LocalDataManager localDataManager;
    private final RxResultHandler rxResultHandler;

    @Inject
    public FeedBackRepositoryImpl(FeedbackApiService feedbackApiService, LocalDataManager localDataManager, RxResultHandler rxResultHandler) {
        this.feedbackApiService = feedbackApiService;
        this.localDataManager = localDataManager;
        this.rxResultHandler = rxResultHandler;
    }

    @Override
    public Single<FeedbackResponse> sendFeedback(String title, String content) {
        return localDataManager.getUserIdAsync()
                .flatMap( userId ->
                        rxResultHandler.handleSingle(feedbackApiService.sendFeedback(new FeedbackRequest(title, content, userId))
                ));
    }
}
