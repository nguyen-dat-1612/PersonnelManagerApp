package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.mapper.NotificationMapper;
import com.managerapp.personnelmanagerapp.data.mapper.NotificationRecipientMapper;
import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.NotificationRequest;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationApiService apiService;
    private final LocalDataManager localDataManager;
    private final RxResultHandler rxResultHandler;
    @Inject
    public NotificationRepositoryImpl(NotificationApiService notificationApiService, LocalDataManager localDataManager, RxResultHandler rxResultHandler) {
        this.apiService = notificationApiService;
        this.localDataManager = localDataManager;
        this.rxResultHandler = rxResultHandler;
    }

    public Single<PagedModel<NotificationRecipient>> getNotifications(int page, int size) {
        return localDataManager.getUserIdAsync()
                .flatMap( userId ->
                        rxResultHandler.handleSingle(apiService.getAllUserNotifications(userId, page, size))
                                .map(NotificationRecipientMapper::toPagedModel)
                );
    }

    public Single<Notification> getNotificationRecipient(long notificationId) {
        return rxResultHandler.handleSingle(apiService.getNotificationRecipient(notificationId))
                .map(NotificationMapper::toNotification);
    }

    @Override
    public Single<Notification> getNotification(long notificationId) {
        return rxResultHandler.handleSingle(apiService.getNotification(notificationId))
                .map(NotificationMapper::toNotification);
    }

    public Single<Boolean> markNotificationsAsSeen(List<Integer> notificationIds) {
        return rxResultHandler.handleSingle(apiService.markNotificationsAsSeen(notificationIds));
    }


    public Maybe<Boolean> createNotification(
            String title,
            String content,
            String recipientText,
            List<String> attached,
            List<Long> receiverIds,
            List<String> positionIds,
            List<String> departmentIds
    ) {
        return localDataManager.getUserIdAsync()
                .toMaybe()
                .flatMap(userId ->
                        apiService.createNotification(
                                new NotificationRequest(
                                        title, content, userId, recipientText, attached,
                                        receiverIds, positionIds, departmentIds
                                )
                        )
                )
                .map(apiResponse -> apiResponse.getCode() == 200);
    }

    public Single<PagedModel<NotificationRecipient>> getSenderNotifications(int page, int size, String type) {
        return localDataManager.getUserIdAsync()
                .subscribeOn(Schedulers.io())
                .flatMap(userId ->
                        rxResultHandler.handleSingle(apiService.getAllSenderNotifications(userId, page, size, type))
                                .map(NotificationRecipientMapper::toPagedModel)
                );
    }

}
