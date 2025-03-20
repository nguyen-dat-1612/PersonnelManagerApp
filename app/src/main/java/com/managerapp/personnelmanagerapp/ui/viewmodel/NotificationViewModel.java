package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.usecase.GetAllNotificationsUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class NotificationViewModel extends ViewModel {
    private final GetAllNotificationsUseCase getAllNotificationsUseCase;
    private final MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();

    @Inject
    public NotificationViewModel(GetAllNotificationsUseCase getAllNotificationsUseCase) {
        this.getAllNotificationsUseCase = getAllNotificationsUseCase;
    }

    private LiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public void loadAllNotifications() {
        if (notifications != null ) {
            getAllNotificationsUseCase.execute()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            notifications -> this.notifications.setValue(notifications),
                            throwable ->  { // Xử lý lỗi

                            }
                    );
        }
    }
}
