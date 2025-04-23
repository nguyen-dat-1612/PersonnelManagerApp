package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientDao;
import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientEntity;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.domain.model.Notification;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

@Singleton
public class NotificationRepository {
    private final NotificationApiService notificationApiService;
    private final NotificationRecipientDao dao;
    private static final String TAG = "NotificationRepository";
    private long userId;

    @Inject
    public NotificationRepository(NotificationApiService notificationApiService,
                                  NotificationRecipientDao dao) {
        this.notificationApiService = notificationApiService;
        this.dao = dao;
    }

    public Flowable<List<NotificationRecipientEntity>> getNotifications(long userId) {
        return notificationApiService.getAllUserNotifications(userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> Log.d(TAG, "🛜 Đang gọi API..."))
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        List<NotificationRecipientEntity> apiData = response.body().getData();
                        Log.d(TAG, "✅ Nhận " + apiData.size() + " items từ API");

                        return dao.deleteAll()
                                .andThen(dao.insertAll(apiData))
                                .doOnComplete(() -> Log.d(TAG, "💾 Đã cập nhật Room"))
                                .andThen(dao.getAllNotifications());
                    }
                    return Flowable.error(new HttpException(response));
                })
                .onErrorResumeNext(error -> {
                    Log.e(TAG, "❌ Lỗi API, hiển thị dữ liệu từ Room", error);
                    return dao.getAllNotifications();
                })
                .doOnNext(data -> Log.d(TAG, "📦 Hiển thị: " + data.size() + " items"));
    }

    public Single<Notification> getNotification(long userId, long notificationId) {
        return notificationApiService.getNotification(userId)
                .subscribeOn(Schedulers.io())
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null && response.body().getCode() == 200) {
                        Log.d(TAG, "✅ Nhận thông báo từ API : " + response.body().getData());

                        // Backend đã đánh dấu đọc rồi, chỉ cần cập nhật local DB
                        return dao.markAsRead(notificationId)
                                .doOnComplete(() -> Log.d(TAG, "✅ Đánh dấu đã đọc thành công trong Room cho notificationId: " + notificationId))
                                .doOnError(error -> Log.e(TAG, "❌ Lỗi khi đánh dấu đã đọc trong Room", error))
                                .andThen(Single.just(response.body().getData()));
                    }
                    Log.e(TAG, "❌ Lỗi API, hiển thị thông báo từ Room");
                    return Single.error(new Exception("Invalid API response: " + response.code()));
                })
                .doOnError(error -> Log.e(TAG, "Error fetching notification", error));
    }
}
