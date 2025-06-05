package com.managerapp.personnelmanagerapp.presentation.notification.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetNotificationRecipientUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.MarkNotificationUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private static final String TAG = "NotificationViewModel";
    private final GetNotificationRecipientUseCase getNotificationUseCase;
    private final MarkNotificationUseCase markAsReadUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<UiState<Notification>> uiState = new MutableLiveData<>();
    private final LocalDataManager localDataManager;
    @Inject
    public NotificationViewModel(GetNotificationRecipientUseCase getNotificationUseCase, LocalDataManager localDataManager, MarkNotificationUseCase markAsReadUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
        this.localDataManager = localDataManager;
        this.markAsReadUseCase = markAsReadUseCase;
    }

    public LiveData<UiState<Notification>> getUiState() {
        return uiState;
    }

    public void loadNotification(long notificationId) {
        uiState.setValue(UiState.Loading.getInstance());
        disposable.add(getNotificationUseCase.execute(notificationId)
                        .subscribeOn(Schedulers.io())
                        .timeout(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
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

    public void markAsRead(long notificationId) {
        disposable.add(markAsReadUseCase.execute(List.of((int) notificationId))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .timeout(5, TimeUnit.SECONDS)
                        .subscribe(
                                notification -> {
                                    if (notification != null) {
                                    } else {
                                    }
                                },
                                throwable -> {}
                        ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
