package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.local.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class AuthModule {

    @Provides
    @Singleton
    public static AuthApiService provideAuthApiService(Retrofit retrofit) {
        return retrofit.create(AuthApiService.class);
    }

    @Provides
    @Singleton
    public static AuthRepository provideAuthRepository(AuthApiService authApiService, SecureTokenManager secureTokenManager) {
        return new AuthRepository(authApiService, secureTokenManager);
    }
}
