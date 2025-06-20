package com.managerapp.personnelmanagerapp.domain.usecase.feedback;

import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.FeedbackResponse;
import com.managerapp.personnelmanagerapp.data.repository.FeedBackRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.repository.FeedBackRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class SendFeedbackUseCase {
    private final FeedBackRepository feedbackRepository;

    @Inject
    public SendFeedbackUseCase(FeedBackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Single<FeedbackResponse> execute(String title, String content) {
        return feedbackRepository.sendFeedback(title, content);
    }
}
