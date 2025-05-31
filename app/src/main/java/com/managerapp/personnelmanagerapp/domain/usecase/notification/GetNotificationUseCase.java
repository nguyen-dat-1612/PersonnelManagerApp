package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetNotificationUseCase {
    private final NotificationRepository repository;
    @Inject
    public GetNotificationUseCase(NotificationRepository notificationRepository) {
        this.repository = notificationRepository;
    }

    public Single<Notification> execute(long notificationId){
        return repository.getNotification( notificationId);
    }
}
