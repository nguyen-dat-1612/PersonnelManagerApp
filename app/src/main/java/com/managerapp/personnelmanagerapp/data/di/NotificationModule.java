package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.local.LocalDataManager;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
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
    public static NotificationRepository provideNotificationRepository(NotificationApiService notificationApiService, LocalDataManager localDataManager) {
        return new NotificationRepository(notificationApiService, localDataManager);
    }
}
