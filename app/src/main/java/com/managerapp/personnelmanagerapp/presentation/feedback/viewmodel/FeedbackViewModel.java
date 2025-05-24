package com.managerapp.personnelmanagerapp.presentation.feedback.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.FeedbackRequest;
import com.managerapp.personnelmanagerapp.domain.usecase.feedback.SendFeedbackUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class FeedbackViewModel extends ViewModel {
    private final SendFeedbackUseCase sendFeedbackUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private static final String TAG = "FeedbackViewModel";
    private final LocalDataManager localDataManager;

    private final MutableLiveData<UiState<String>> uiState = new MutableLiveData<>();


    public MutableLiveData<UiState<String>> getUiState() {
        return uiState;
    }

    @Inject
    public FeedbackViewModel(SendFeedbackUseCase sendFeedbackUseCase, LocalDataManager localDataManager) {
        this.sendFeedbackUseCase = sendFeedbackUseCase;
        this.localDataManager = localDataManager;
    }

    public void sendFeedback(String title, String content) {
        uiState.setValue(UiState.Loading.getInstance()); // Báo UI là đang gửi

        disposables.add(
                Single.fromCallable(() -> {
                    Long rawUserId = localDataManager.getUserId();
                    if (rawUserId == null ) {
                        Log.e(TAG, "User ID is missing" + rawUserId);
                        throw new IllegalArgumentException("User ID is missing");
                    }
                    return rawUserId;
                })
                .flatMap(userId -> {
                    if (userId <= 0) {
                        return Single.error(new Throwable("Invalid user ID"));
                    }
                    Log.d(TAG, "User ID: " + userId);
                    FeedbackRequest feedbackRequest = new FeedbackRequest(title, content, userId);
                    return sendFeedbackUseCase.execute(feedbackRequest);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5, TimeUnit.SECONDS)
                .doOnSubscribe(disposable -> uiState.setValue(UiState.Loading.getInstance()))
                .subscribe(
                        response -> {
                            if (response != null) {
                                uiState.setValue(new UiState.Success("Gửi thành công!"));
                            } else {
                                uiState.setValue(new UiState.Error("Gui thất bại!"));
                            }
                        },
                        throwable -> {
                            Log.e(TAG, "Lỗi khi gửi phản hồi: ", throwable);
                            uiState.setValue(new UiState.Error("Lỗi hệ thống: " + throwable.getMessage()));
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
