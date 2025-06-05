package com.managerapp.personnelmanagerapp.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.managerapp.personnelmanagerapp.manager.EncryptionManager;
import com.managerapp.personnelmanagerapp.manager.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.request.TokenRefreshRequest;
import com.managerapp.personnelmanagerapp.manager.SessionManager;
import com.managerapp.personnelmanagerapp.manager.SettingsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class StorageModule {

    @Provides
    @Singleton
    public static EncryptionManager provideEncryptionManager() {
        return new EncryptionManager();
    }

    @Provides
    @Singleton
    public static SecureTokenManager provideSecureTokenManager(EncryptionManager encryptionManager, SharedPreferences sharedPreferences) {
        return new SecureTokenManager(encryptionManager, sharedPreferences);
    }

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public static TokenRefreshRequest provideTokenRefreshRequest() {
        return new TokenRefreshRequest();
    }

    @Provides
    @Singleton
    public static SessionManager provideSessionManager(@ApplicationContext Context context) {
        return new SessionManager(context);
    }

    @Provides
    @Singleton
    public static SettingsManager provideSettingsManager(@ApplicationContext Context context) {
        return new SettingsManager(context);
    }
}
