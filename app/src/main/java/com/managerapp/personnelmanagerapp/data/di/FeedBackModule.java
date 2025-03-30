package com.managerapp.personnelmanagerapp.data.di;

import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.repository.ContractRepository;
import com.managerapp.personnelmanagerapp.data.repository.FeedBackRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class FeedBackModule {

    @NonNull
    @Provides
    @Singleton
    public static FeedbackApiService provideFeedbackApiService(Retrofit retrofit) {
        return retrofit.create(FeedbackApiService.class);
    }

    @Provides
    @Singleton
    public static FeedBackRepository provideFeedBackRepository(FeedbackApiService feedbackApi) {
        return new FeedBackRepository(feedbackApi);
    }
}
