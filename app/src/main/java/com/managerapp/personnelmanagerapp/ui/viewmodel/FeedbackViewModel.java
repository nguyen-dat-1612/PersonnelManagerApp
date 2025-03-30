package com.managerapp.personnelmanagerapp.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.domain.usecase.SendFeedbackUseCase;
import com.managerapp.personnelmanagerapp.ui.state.FeedbackState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class FeedbackViewModel extends ViewModel {
    private final SendFeedbackUseCase sendFeedbackUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private static final String TAG = "FeedbackViewModel";

    private final MutableLiveData<FeedbackState> feedbackState = new MutableLiveData<>();

    public LiveData<FeedbackState> getFeedbackState() {
        return feedbackState;
    }
    @Inject
    public FeedbackViewModel(SendFeedbackUseCase sendFeedbackUseCase) {
        this.sendFeedbackUseCase = sendFeedbackUseCase;
    }

    public void sendFeedback(FeedbackRequest feedbackRequest) {
        feedbackState.setValue(FeedbackState.Loading.getInstance()); // Báo UI là đang gửi

        disposables.add(sendFeedbackUseCase.execute(feedbackRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> {
                            if (success) {
                                feedbackState.setValue(new FeedbackState.Success("Gửi thành công!"));
                            } else {
                                feedbackState.setValue(new FeedbackState.Error("Gửi thất bại!"));
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Lỗi khi gửi phản hồi: ", throwable);
                            feedbackState.setValue(new FeedbackState.Error("Lỗi hệ thống: " + throwable.getMessage()));
                        }
                )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

}
