package com.managerapp.personnelmanagerapp.di.usecase;


import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;
import com.managerapp.personnelmanagerapp.domain.usecase.LoginUseCase;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AuthUseCaseModule {

    @Provides
    @Singleton
    public static LoginUseCase provideLoginUseCase(AuthRepository authRepository) {
        return new LoginUseCase(authRepository);
    }
}