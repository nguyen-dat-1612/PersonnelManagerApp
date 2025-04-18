package com.managerapp.personnelmanagerapp.presentation.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.domain.usecase.GetAllUserNotificationsUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotificationRecipientViewModel extends ViewModel {
    private static String TAG = "NotificationViewModel";
    private final GetAllUserNotificationsUseCase getAllNotificationsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<List<NotificationRecipientEntity>>> uiState = new MutableLiveData<>();
    private boolean isDataLoaded = false;

    public LiveData<UiState<List<NotificationRecipientEntity>>> getUiState() {
        return uiState;
    }

    @Inject
    public NotificationRecipientViewModel(GetAllUserNotificationsUseCase getAllNotificationsUseCase) {
        this.getAllNotificationsUseCase = getAllNotificationsUseCase;
    }

    public void loadAllNotifications() {
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(
                getAllNotificationsUseCase.execute()
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
        isDataLoaded = true;
    }

    public void forceRefresh() {
        isDataLoaded = false;
        loadAllNotifications();
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear(); // Hủy tất cả request khi ViewModel bị hủy
    }
}
