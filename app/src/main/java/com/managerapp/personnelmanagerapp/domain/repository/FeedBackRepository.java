package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.FeedbackResponse;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;

import io.reactivex.rxjava3.core.Single;

public interface FeedBackRepository {
    Single<FeedbackResponse> sendFeedback(String title, String content);
}
