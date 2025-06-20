package com.managerapp.personnelmanagerapp.domain.repository;

import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface NotificationRepository {
    Single<PagedModel<NotificationRecipient>> getNotifications( int page, int size);
    Single<Notification> getNotificationRecipient(long notificationId);
    Single<Notification> getNotification(long notificationId);
    Single<Boolean> markNotificationsAsSeen(List<Integer> notificationIds);
    Maybe<Boolean> createNotification (
            String title,
            String content,
            String recipientText,
            List<String> attached,
            List<Long> receiverIds,
            List<String> positionIds,
            List<String> departmentIds
    );
    Single<PagedModel<NotificationRecipient>> getSenderNotifications(int page, int size, String type);
}
