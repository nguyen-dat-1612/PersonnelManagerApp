package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.remote.api.RewardAssignmentApiService;
import com.managerapp.personnelmanagerapp.data.repository.RewardAssignmentRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class RewardModule {

    @Provides
    @Singleton
    public static RewardAssignmentApiService provideRewardApiService(Retrofit retrofit) {
        return retrofit.create(RewardAssignmentApiService.class);
    }

    @Provides
    @Singleton
    public static RewardAssignmentRepository provideRewardRepository(RewardAssignmentApiService rewardAssignmentApiService) {
        return new RewardAssignmentRepository(rewardAssignmentApiService);
    }
}
