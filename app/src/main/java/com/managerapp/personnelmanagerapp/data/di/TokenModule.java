package com.managerapp.personnelmanagerapp.data.di;

import android.content.SharedPreferences;

import com.managerapp.personnelmanagerapp.data.manager.EncryptionManager;
import com.managerapp.personnelmanagerapp.data.manager.SecureTokenManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class TokenModule {

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
}
