package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.FeedbackResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class FeedBackRepository {
    private final FeedbackApiService feedbackApiService;
    private final String TAG = "FeedBackRepository";

    @Inject
    public FeedBackRepository(FeedbackApiService feedbackApiService) {
        this.feedbackApiService = feedbackApiService;
    }

    public Single<BaseResponse<FeedbackResponse>> sendFeedback(FeedbackRequest feedbackRequest) {
        Log.d(TAG, "Gửi phản hồi: " + feedbackRequest.toString());
        return feedbackApiService.sendFeedback(feedbackRequest)
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null && response.body().getCode() == 200) {
                        Log.d(TAG, "Gửi phản hồi thành công: " + response.body().toString());
                        return Single.just(response.body());
                    } else {
                        Log.e(TAG, "Gửi phản hồi thất bại: " + response.errorBody().toString());
                        return Single.error(new Exception("Gửi phản hồi thất bại"));
                    }
                })
                .doOnError(throwable -> {
                    Log.e(TAG, "Lỗi khi gửi phản hồi: ", throwable); // In lỗi ra Logcat
                });
    }

}
