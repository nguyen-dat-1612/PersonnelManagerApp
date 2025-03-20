package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.remote.ContractApiService;
import com.managerapp.personnelmanagerapp.data.repository.ContractRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class ContractDataModule {

    @Provides
    @Singleton
    public static ContractApiService provideContractApiService(Retrofit retrofit) {
        return retrofit.create(ContractApiService.class);
    }

    @Provides
    @Singleton
    public static ContractRepository provideContractRepository(ContractApiService contractApi) {
        return new ContractRepository(contractApi);
    }

}
