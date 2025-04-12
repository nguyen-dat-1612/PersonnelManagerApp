package com.managerapp.personnelmanagerapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface NotificationRecipientDao {

    @Query("SELECT * FROM notification_recipients ORDER BY sendDate DESC")
    Flowable<List<NotificationRecipientEntity>> getAllNotifications();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<NotificationRecipientEntity> notifications);

    @Query("UPDATE notification_recipients SET readStatus = 1 WHERE id = :id")
    Completable markAsRead(long id);

    @Query("DELETE FROM notification_recipients")
    Completable deleteAll();
}
