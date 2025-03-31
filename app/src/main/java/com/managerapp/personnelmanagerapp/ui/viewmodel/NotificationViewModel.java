package com.managerapp.personnelmanagerapp.ui.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetAllUserNotificationsUseCase;
import com.managerapp.personnelmanagerapp.ui.state.NotificationState;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private final GetAllUserNotificationsUseCase getAllNotificationsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<NotificationState> notificationState = new MutableLiveData<>();

    public LiveData<NotificationState> getNotificationState() {
        return notificationState;
    }
    @Inject
    public NotificationViewModel(GetAllUserNotificationsUseCase getAllNotificationsUseCase) {
        this.getAllNotificationsUseCase = getAllNotificationsUseCase;
    }

    public void loadAllNotifications() {
        notificationState.postValue(new NotificationState.Loading());
        disposables.add(
                getAllNotificationsUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                notifications -> {
                                    if (notifications.isEmpty()) {
                                        Log.d(TAG, "Danh sách thông báo trống");
                                        notificationState.postValue(new NotificationState.Empty());
                                    } else {
                                        notificationState.postValue(new NotificationState.Success(notifications));
                                        Log.d(TAG, "Danh sách thông báo đầy đủ");
                                    }
                                },
                                throwable ->  {
                                    Log.e(TAG, "Đã có lỗi xảy ra: " + throwable.getMessage());
                                    notificationState.postValue(new NotificationState.Error(throwable.getMessage()));

                                }
                        )
        );
    }

    private static String TAG = "NotificationViewModel";
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear(); // Hủy tất cả request khi ViewModel bị hủy
    }
}
