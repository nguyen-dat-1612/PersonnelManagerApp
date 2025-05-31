package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetNotificationRecipientUseCase {
    private final NotificationRepository repository;

    @Inject
    public GetNotificationRecipientUseCase(NotificationRepository repository) {
        this.repository = repository;
    }

    public Single<Notification> execute(long notificationId){
        return repository.getNotificationRecipient( notificationId);
    }
}
