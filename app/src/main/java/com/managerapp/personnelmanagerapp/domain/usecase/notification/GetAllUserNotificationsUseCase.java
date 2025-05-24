package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetAllUserNotificationsUseCase {
    private final NotificationRepository notificationRepository;

    @Inject
    public GetAllUserNotificationsUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Observable<PagedModel<NotificationRecipient>> execute(long userId, int page) {
        return notificationRepository.getNotifications(userId, page, 10)
                .toObservable();
    }
}
