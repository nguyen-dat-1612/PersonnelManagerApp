package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.remote.request.TokenRefreshRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class TokenRefreshRequestModule {
    @Provides
    @Singleton
    public static TokenRefreshRequest provideTokenRefreshRequest() {
        return new TokenRefreshRequest();
    }
}
