package com.managerapp.personnelmanagerapp.data.di;

import android.content.Context;

import com.managerapp.personnelmanagerapp.data.local.UserPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class StorageModule {

    @Provides
    @Singleton
    public static UserPreferences provideUserPreferences(Context context) {
        return new UserPreferences(context);
    }
}
