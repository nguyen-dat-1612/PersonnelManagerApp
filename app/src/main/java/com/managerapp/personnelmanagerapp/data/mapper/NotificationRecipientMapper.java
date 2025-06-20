package com.managerapp.personnelmanagerapp.data.mapper;

import com.managerapp.personnelmanagerapp.data.remote.response.NotificationRecipientResponse;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;

import java.util.stream.Collectors;

public class NotificationRecipientMapper {
    public static NotificationRecipient toNotificationRecipient(NotificationRecipientResponse response) {
        return new NotificationRecipient(
                response.getId(),
                response.isReadStatus(),
                response.getTitle(),
                response.getSendDate()
        );
    }

    public static PagedModel<NotificationRecipient> toPagedModel(PagedModel<NotificationRecipientResponse> response) {
        PagedModel<NotificationRecipient> pagedModel = new PagedModel<>();
        pagedModel.setContent(response.getContent().stream()
                .map(NotificationRecipientMapper::toNotificationRecipient)
                .collect(Collectors.toList()));
        pagedModel.setPage(response.getPage());
        return pagedModel;
    }
}
