package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.remote.request.NotificationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.NotificationRecipientResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.NotificationResponse;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;
import com.managerapp.personnelmanagerapp.domain.model.PagedModel;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import kotlin.Unit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationApiService {

    @GET("notifications/{id}/read")
    Single<BaseResponse<NotificationResponse>> getNotificationRecipient(@Path("id") long id);

    @GET("notifications/{id}")
    Single<BaseResponse<NotificationResponse>> getNotification(@Path("id") long id);

    @GET("notifications/user/{id}")
    Single<BaseResponse<PagedModel<NotificationRecipientResponse>>> getAllUserNotifications(
            @Path("id") long userId,
            @Query("pageNumber") int page,
            @Query("pageSize") int size
    );

    @PUT("notifications/seen")
    Single<BaseResponse<Boolean>> markNotificationsAsSeen(@Body List<Integer> notificationIds);

    @POST("notifications/create")
    Maybe<BaseResponse<Unit>> createNotification (@Body NotificationRequest notificationRequest);

    @GET("notifications/sender/{id}")
    Single<BaseResponse<PagedModel<NotificationRecipientResponse>>> getAllSenderNotifications(
            @Path("id") long senderId,
            @Query("pageNumber") int page,
            @Query("pageSize") int size,
            @Query("type") String type
    );

}
