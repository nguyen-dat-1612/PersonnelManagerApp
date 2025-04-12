package com.managerapp.personnelmanagerapp.data.remote.api;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.Notification;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationApiService {

    @GET("notifications/{id}")
    Single<Response<BaseResponse<Notification>>> getNotification(@Path("id") long id);

    @GET("notifications/user/{id}")
    Flowable<Response<BaseResponse<List<NotificationRecipientEntity>>>> getAllUserNotifications(@Path("id") long userId);

}
