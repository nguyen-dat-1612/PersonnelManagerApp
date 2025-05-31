package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public class CreateNotificationUseCase {

    private final NotificationRepository repository;

    @Inject
    public CreateNotificationUseCase(NotificationRepository notificationRepository) {
        this.repository = notificationRepository;
    }

    public Maybe<Boolean> execute(
            String title,
            String content,
            String recipientText,
            List<String> attached,
            List<Long> receiverIds,
            List<String> positionIds,
            List<String> departmentIds
    ) {
        return repository.createNotification(title, content, recipientText, attached,
                receiverIds, positionIds, departmentIds);
    }
}
