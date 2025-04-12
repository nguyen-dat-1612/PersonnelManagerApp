package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;

import java.util.List;
import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GetAllUserNotificationsUseCase {
    private final NotificationRepository notificationRepository;

    @Inject
    public GetAllUserNotificationsUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public Flowable<List<NotificationRecipientEntity>> execute() {
        return notificationRepository.getNotifications(); // Chuyển từ Flowable sang Observable
    }
}
