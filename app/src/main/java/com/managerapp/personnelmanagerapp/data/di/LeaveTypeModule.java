package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.repository.LeaveTypeRepository;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class LeaveTypeModule {

    @Provides
    @Singleton
    public static LeaveTypeApiService provideLeaveTypeApiService(Retrofit retrofit) {
        return retrofit.create(LeaveTypeApiService.class);
    }

    @Provides
    @Singleton
    public static LeaveTypeRepository provideLeaveTypeRepository(LeaveTypeApiService leaveTypeApiService) {
        return new LeaveTypeRepository(leaveTypeApiService);
    }

}
