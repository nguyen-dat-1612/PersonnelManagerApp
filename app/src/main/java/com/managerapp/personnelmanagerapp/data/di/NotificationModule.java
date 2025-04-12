package com.managerapp.personnelmanagerapp.data.di;

import android.content.Context;

import androidx.room.Room;

import com.managerapp.personnelmanagerapp.data.local.AppDatabase;
import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientDao;
import com.managerapp.personnelmanagerapp.data.manager.LocalDataManager;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class NotificationModule {

    @Provides
    @Singleton
    public static NotificationApiService provideNotificationApiService(Retrofit retrofit) {
        return retrofit.create(NotificationApiService.class);
    }

    @Provides
    @Singleton
    public static NotificationRepository provideNotificationRepository(NotificationApiService notificationApiService, LocalDataManager localDataManager, NotificationRecipientDao notificationRecipientDao) {
        return new NotificationRepository(notificationApiService, localDataManager, notificationRecipientDao);
    }
}
