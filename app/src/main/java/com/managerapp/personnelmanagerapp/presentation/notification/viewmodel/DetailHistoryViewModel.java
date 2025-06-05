package com.managerapp.personnelmanagerapp.presentation.notification.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetNotificationUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DetailHistoryViewModel extends ViewModel {
    private final GetNotificationUseCase getNotificationUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<UiState<Notification>> uiState = new MutableLiveData<>();
    @Inject
    public DetailHistoryViewModel(GetNotificationUseCase getNotificationUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
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
                            } else {
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
