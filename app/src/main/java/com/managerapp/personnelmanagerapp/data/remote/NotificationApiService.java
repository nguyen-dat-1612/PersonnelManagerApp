package com.managerapp.personnelmanagerapp.data.remote;

import com.managerapp.personnelmanagerapp.data.remote.response.NotificationResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface NotificationApiService {
    @GET("notications")
    Single<Response<NotificationResponse>> getNotifications();
}
