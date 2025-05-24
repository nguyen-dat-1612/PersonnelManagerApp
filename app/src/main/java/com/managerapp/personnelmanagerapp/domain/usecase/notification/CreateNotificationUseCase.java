package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class CreateNotificationUseCase {

    private final NotificationRepository notificationRepository;

    @Inject
    public CreateNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Single<Boolean> execute(
            String title,
            String content,
            String recipientText,
            List<String> attached,
            List<Long> receiverIds,
            List<String> positionIds,
            List<String> departmentIds
    ) {
        return notificationRepository.createNotification(title, content, recipientText, attached,
                receiverIds, positionIds, departmentIds);
    }
}
