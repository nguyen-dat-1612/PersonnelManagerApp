package com.managerapp.personnelmanagerapp.data.repository;

import com.managerapp.personnelmanagerapp.data.utils.RxResultHandler;
import com.managerapp.personnelmanagerapp.data.remote.request.NotificationRequest;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;

@Singleton
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationApiService apiService;
    private final LocalDataManager localDataManager;
    private static final String TAG = "NotificationRepository";
    private long userId;

    PublishSubject<Integer> pageSubject = PublishSubject.create();

    @Inject
    public NotificationRepositoryImpl(NotificationApiService notificationApiService, LocalDataManager localDataManager) {
        this.apiService = notificationApiService;
        this.localDataManager = localDataManager;
    }

    public Single<PagedModel<NotificationRecipient>> getNotifications(long userId, int page, int size) {
        return RxResultHandler.handle(apiService.getAllUserNotifications(userId, page, size));
    }

    public Single<Notification> getNotificationRecipient(long notificationId) {
        return RxResultHandler.handle(apiService.getNotificationRecipient(notificationId));
    }

    @Override
    public Single<Notification> getNotification(long notificationId) {
        return RxResultHandler.handle(apiService.getNotification(notificationId));
    }

    public Single<Boolean> markNotificationsAsSeen(List<Integer> notificationIds) {
        return RxResultHandler.handle(apiService.markNotificationsAsSeen(notificationIds));
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
                .flatMap(userId -> RxResultHandler.handle(apiService.getAllSenderNotifications(userId, page, size, type)));
    }

}
