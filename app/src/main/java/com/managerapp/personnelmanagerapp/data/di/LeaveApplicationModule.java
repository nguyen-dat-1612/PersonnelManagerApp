package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.repository.LeaveApplicationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class LeaveApplicationModule {

    @Provides
    @Singleton
    public static LeaveApplicationApiService leaveApplicationApiService (Retrofit retrofit) {
        return retrofit.create(LeaveApplicationApiService.class);
    }

    @Provides
    @Singleton
    public static LeaveApplicationRepository leaveApplicationRepository( LeaveApplicationApiService leaveApplicationApiService) {
        return new LeaveApplicationRepository(leaveApplicationApiService);
    }
}
