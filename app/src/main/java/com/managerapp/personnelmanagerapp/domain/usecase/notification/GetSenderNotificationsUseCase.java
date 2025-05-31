package com.managerapp.personnelmanagerapp.domain.usecase.notification;

import com.managerapp.personnelmanagerapp.data.repository.NotificationRepositoryImpl;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class GetSenderNotificationsUseCase {
    private final NotificationRepository repository;

    @Inject
    public GetSenderNotificationsUseCase(NotificationRepository repository) {
        this.repository = repository;
    }

    public Single<PagedModel<NotificationRecipient>> execute(int page, int size, String type) {
        return repository.getSenderNotifications( page, size, type);
    }
}