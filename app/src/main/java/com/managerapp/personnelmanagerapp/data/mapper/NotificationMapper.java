package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.NotificationResponse;
import com.managerapp.personnelmanagerapp.domain.model.Notification;

public class NotificationMapper {
    public static Notification toNotification(NotificationResponse response) {
        return new Notification.Builder()
                .id(response.getId())
                .title(response.getTitle())
                .content(response.getContent())
                .attached(response.getAttached())
                .sendDate(response.getSendDate())
                .sender(UserSummaryMapper.toUserSummary(response.getSender()))
                .build();
    }


}
