package com.managerapp.personnelmanagerapp.data.di;

import com.managerapp.personnelmanagerapp.data.remote.api.DisciplineAssignmentApiService;
import com.managerapp.personnelmanagerapp.data.repository.DisciplineAssignmentRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class DisciplineModule {

    @Provides
    @Singleton
    public static DisciplineAssignmentApiService provideDisciplineApiService(Retrofit retrofit) {
        return retrofit.create(DisciplineAssignmentApiService.class);
    }

    @Provides
    @Singleton
    public static DisciplineAssignmentRepository provideDisciplineRepository(DisciplineAssignmentApiService disciplineAssignmentApiService) {
        return new DisciplineAssignmentRepository(disciplineAssignmentApiService);
    }
}
