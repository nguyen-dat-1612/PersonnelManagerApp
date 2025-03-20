package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.remote.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.NotificationApiService;
import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class NotificationDataModule {

    @Provides
    @Singleton
    public static NotificationApiService provideNotificationApiService(Retrofit retrofit) {
        return retrofit.create(NotificationApiService.class);
    }

    @Provides
    @Singleton
    public static NotificationRepository provideNotificationRepository(NotificationApiService notificationApiService) {
        return new NotificationRepository(notificationApiService);
    }
}
