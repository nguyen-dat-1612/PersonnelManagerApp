package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;

import java.util.List;
import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class GetAllUserNotificationsUseCase {
    private final NotificationRepository notificationRepository;

    @Inject
    public GetAllUserNotificationsUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public Flowable<List<NotificationRecipientEntity>> execute(long userId) {
        return notificationRepository.getNotifications(userId);
    }
}
