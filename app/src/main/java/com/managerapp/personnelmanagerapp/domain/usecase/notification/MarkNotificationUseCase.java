package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class MarkNotificationUseCase {
    private final NotificationRepository repository;

    @Inject
    public MarkNotificationUseCase(NotificationRepository notificationRepository) {
        this.repository = notificationRepository;
    }

    public Single<Boolean> execute(List<Integer> notificationIds) {
        return repository.markNotificationsAsSeen(notificationIds);
    }

}
