package com.managerapp.personnelmanagerapp.di.repository;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import com.managerapp.personnelmanagerapp.data.remote.AuthApiService;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;

@Module
@InstallIn(SingletonComponent.class)
public class AuthRepositoryModule {
    @Provides
    @Singleton
    public static AuthRepository provideAuthRepository(AuthApiService authApiService) {
        return new AuthRepository(authApiService);
    }
}
