package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.RequestResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

@Singleton
public class FeedBackRepository {
    private final FeedbackApiService feedbackApiService;
    private final String TAG = "FeedBackRepository";

    @Inject
    public FeedBackRepository(FeedbackApiService feedbackApiService) {
        this.feedbackApiService = feedbackApiService;
    }

    public Single<Boolean> sendFeedback(FeedbackRequest feedbackRequest) {
        return feedbackApiService.sendFeedback(feedbackRequest)
                .subscribeOn(Schedulers.io())
                .map(response -> {
                    return "success".equalsIgnoreCase(response.body().getStatus()); // Nếu "success" -> true
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "Lỗi khi gửi phản hồi: ", throwable); // In lỗi ra Logcat
                })
                .onErrorReturnItem(false); // Nếu có lỗi -> trả về false
    }

}
