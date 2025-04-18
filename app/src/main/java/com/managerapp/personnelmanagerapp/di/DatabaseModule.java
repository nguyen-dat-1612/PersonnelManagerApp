package com.managerapp.personnelmanagerapp.di;

import android.content.Context;

import androidx.room.Room;

import com.managerapp.personnelmanagerapp.data.local.AppDatabase;
import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "app_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public static NotificationRecipientDao provideNotificationRecipientDao(AppDatabase database) {
        return database.notificationRecipientDao();
    }

}
