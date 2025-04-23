package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.domain.model.Notification;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetNotificationUseCase {
    private final NotificationRepository repository;

    @Inject
    public GetNotificationUseCase(NotificationRepository repository) {
        this.repository = repository;
    }

    public Single<Notification> execute(@NonNull long userId, long notificationId){
        return repository.getNotification(userId, notificationId);
    }
}
