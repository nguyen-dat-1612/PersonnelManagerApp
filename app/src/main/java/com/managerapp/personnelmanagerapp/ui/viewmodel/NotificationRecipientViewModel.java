package com.managerapp.personnelmanagerapp.ui.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetAllUserNotificationsUseCase;
import com.managerapp.personnelmanagerapp.ui.state.NotificationRecipientState;

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
    private final MutableLiveData<NotificationRecipientState> notificationState = new MutableLiveData<>();
    private boolean isDataLoaded = false;
    public LiveData<NotificationRecipientState> getNotificationState() {
        return notificationState;
    }
    @Inject
    public NotificationRecipientViewModel(GetAllUserNotificationsUseCase getAllNotificationsUseCase) {
        this.getAllNotificationsUseCase = getAllNotificationsUseCase;
    }

    public void loadAllNotifications() {
        notificationState.postValue(new NotificationRecipientState.Loading());
        disposables.add(
                getAllNotificationsUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                notifications -> {
                                    if (notifications.isEmpty()) {
                                        Log.d(TAG, "Danh sách thông báo trống");
                                        notificationState.postValue(new NotificationRecipientState.Empty());
                                    } else {
                                        notificationState.postValue(new NotificationRecipientState.Success(notifications));
                                        Log.d(TAG, "Danh sách thông báo đầy đủ");
                                    }
                                },
                                throwable ->  {
                                    Log.e(TAG, "Đã có lỗi xảy ra: " + throwable.getMessage());
                                    notificationState.postValue(new NotificationRecipientState.Error(throwable.getMessage()));

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
