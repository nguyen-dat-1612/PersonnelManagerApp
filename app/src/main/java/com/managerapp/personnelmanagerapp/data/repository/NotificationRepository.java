package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.local.LocalDataManager;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.domain.model.NotificationRecipient;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class NotificationRepository {
    private final LocalDataManager localDataManager;
    private final NotificationApiService notificationApiService;
    private static final String TAG = "NotificationRepository";
    @Inject
    public NotificationRepository(NotificationApiService notificationApiService, LocalDataManager localDataManager) {
        this.localDataManager = localDataManager;
        this.notificationApiService = notificationApiService;
    }


    public Single<List<NotificationRecipient>> getAllUserNotifications() {
        return notificationApiService.getAllUserNotifications(Long.parseLong(localDataManager.getUserId()))
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thành công");
                        Log.d(TAG, response.body().getData().toString());
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
