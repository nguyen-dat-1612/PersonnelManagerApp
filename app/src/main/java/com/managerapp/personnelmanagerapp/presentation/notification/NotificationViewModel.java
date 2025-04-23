package com.managerapp.personnelmanagerapp.presentation.notification;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetNotificationUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private static final String TAG = "NotificationViewModel";
    private final GetNotificationUseCase getNotificationUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<UiState<Notification>> uiState = new MutableLiveData<>();
    private final LocalDataManager localDataManager;
    @Inject
    public NotificationViewModel(GetNotificationUseCase getNotificationUseCase, LocalDataManager localDataManager) {
        this.getNotificationUseCase = getNotificationUseCase;
        this.localDataManager = localDataManager;
    }

    public LiveData<UiState<Notification>> getUiState() {
        return uiState;
    }

    public void loadNotification(long notificationId) {
        disposable.add(Single.fromCallable(() -> {
                            long userId = Long.parseLong(localDataManager.getUserId());
                            return userId;
                        })
                        .flatMap(userId -> getNotificationUseCase.execute(userId, notificationId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(disposable -> uiState.setValue(UiState.Loading.getInstance()))
                        .subscribe(
                                notification -> {
                                    if (notification != null) {
                                        uiState.setValue(new UiState.Success(notification));
                                        Log.d(TAG, notification.toString());
                                    } else {
                                        Log.d(TAG, "Thông báo không tồn tại");
                                        uiState.setValue(new UiState.Error("Thông báo không tồn tại"));
                                    }
                                },
                                throwable -> uiState.setValue(new UiState.Error(throwable.getMessage()))
                        )
        );
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
