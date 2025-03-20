package com.managerapp.personnelmanagerapp.domain.usecase;

import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Single;

public class GetAllNotificationsUseCase {
    private final NotificationRepository notificationRepository;

    @Inject
    public GetAllNotificationsUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    public Single<List<Notification>> execute() {
        return notificationRepository.getNotifications();
    }
}
