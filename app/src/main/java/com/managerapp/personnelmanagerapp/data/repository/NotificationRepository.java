package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.NotificationApiService;
import com.managerapp.personnelmanagerapp.domain.model.Notification;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;

@Singleton
public class NotificationRepository {
    private final NotificationApiService notificationApiService;
    private static final String TAG = "NotificationRepository";
    @Inject
    public NotificationRepository(NotificationApiService notificationApi) {
        this.notificationApiService = notificationApi;
    }

    public Single<List<Notification>> getNotifications() {
        return notificationApiService.getNotifications()
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thành công");
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Lấy dữ liệu thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }
}
