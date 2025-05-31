package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetAllUserNotificationsUseCase {
    private final NotificationRepository repository;

    @Inject
    public GetAllUserNotificationsUseCase(NotificationRepository notificationRepository) {
        this.repository = notificationRepository;
    }

    public Observable<PagedModel<NotificationRecipient>> execute(long userId, int page) {
        return repository.getNotifications(userId, page, 10)
                .toObservable();
    }
}
