package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.NotificationRequest;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

@Singleton
public class NotificationRepository {
    private final NotificationApiService notificationApiService;
    private final LocalDataManager localDataManager;
    private static final String TAG = "NotificationRepository";
    private long userId;

    PublishSubject<Integer> pageSubject = PublishSubject.create();

    @Inject
    public NotificationRepository(NotificationApiService notificationApiService, LocalDataManager localDataManager) {
        this.notificationApiService = notificationApiService;
        this.localDataManager = localDataManager;
    }

    public Single<PagedModel<NotificationRecipient>> getNotifications(long userId, int page, int size) {
        return RxResultHandler.handle(notificationApiService.getAllUserNotifications(userId, page, size));
    }

    public Single<Notification> getNotification(long notificationId) {
        return RxResultHandler.handle(notificationApiService.getNotification(notificationId));
    }

    public Single<Boolean> markNotificationsAsSeen(List<Integer> notificationIds) {
        return RxResultHandler.handle(notificationApiService.markNotificationsAsSeen(notificationIds));
    }


    public Single<Boolean> createNotification (
            String title,
            String content,
            String recipientText,
            List<String> attached,
            List<Long> receiverIds,
            List<String> positionIds,
            List<String> departmentIds
    ) {
        return localDataManager.getUserIdAsync()
                .flatMap(userId -> RxResultHandler.handle(notificationApiService.createNotification(
                            new NotificationRequest(
                                    title, content, userId, recipientText, attached,
                                    receiverIds, positionIds, departmentIds
                            )
                    )));
    }

    public Single<PagedModel<NotificationRecipient>> getSenderNotifications(int page, int size, String type) {
        return localDataManager.getUserIdAsync()
                .subscribeOn(Schedulers.io())
                .flatMap(userId -> RxResultHandler.handle(notificationApiService.getAllSenderNotifications(userId, page, size, type)));
    }

}
