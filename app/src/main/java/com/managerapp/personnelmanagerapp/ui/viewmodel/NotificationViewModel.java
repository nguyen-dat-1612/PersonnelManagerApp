package com.managerapp.personnelmanagerapp.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetNotificationUseCase;
import com.managerapp.personnelmanagerapp.ui.state.NotificationState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private static final String TAG = "NotificationViewModel";
    private final GetNotificationUseCase getNotificationUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<NotificationState> notificationState = new MutableLiveData<>();

    @Inject
    public NotificationViewModel(GetNotificationUseCase getNotificationUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
    }

    public LiveData<NotificationState> getNotificationState() {
        return notificationState;
    }

    public void loadNotification(long id) {
        notificationState.setValue(NotificationState.Loading.getInstance());

        disposable.add(getNotificationUseCase.execute(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( notification -> {
                    if (notification != null) {
                        notificationState.postValue(new NotificationState.Success(notification));
                        Log.d(TAG, notification.toString());
                    } else {
                        Log.d(TAG, "Thông báo không tồn tại");
                        notificationState.postValue(new NotificationState.Error("Thông báo không tồn tại"));
                    }
                },
                        throwable -> notificationState.postValue(new NotificationState.Error(throwable.getMessage()))
                )
        );
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
