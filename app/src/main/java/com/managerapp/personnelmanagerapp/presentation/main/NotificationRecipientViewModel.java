package com.managerapp.personnelmanagerapp.presentation.main;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.GetAllUserNotificationsUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;

import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotificationRecipientViewModel extends ViewModel {
    private static final String TAG = "NotificationViewModel";

    private final GetAllUserNotificationsUseCase getAllNotificationsUseCase;
    private final LocalDataManager localDataManager;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<UiState<List<NotificationRecipientEntity>>> uiState = new MutableLiveData<>();
    public LiveData<UiState<List<NotificationRecipientEntity>>> getUiState() {
        return uiState;
    }

    private long userId = -1;

    @Inject
    public NotificationRecipientViewModel(GetAllUserNotificationsUseCase getAllNotificationsUseCase, LocalDataManager localDataManager) {
        this.getAllNotificationsUseCase = getAllNotificationsUseCase;
        this.localDataManager = localDataManager;

        try {
            this.userId = Long.parseLong(localDataManager.getUserId());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Lỗi parse userId: " + e.getMessage());
        }
    }

    public void loadAllNotifications() {
        uiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                getAllNotificationsUseCase.execute(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                notifications -> {
                                    if (notifications.isEmpty()) {
                                        Log.d(TAG, "Danh sách thông báo trống");
                                        uiState.postValue(new UiState.Error("Danh sách thông báo trống"));
                                    } else {
                                        uiState.postValue(new UiState.Success(notifications));
                                        Log.d(TAG, "Danh sách thông báo đầy đủ");
                                    }
                                },
                                throwable ->  {
                                    Log.e(TAG, "Đã có lỗi xảy ra: " + throwable.getMessage());
                                    uiState.postValue(new UiState.Error(throwable.getMessage()));

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
