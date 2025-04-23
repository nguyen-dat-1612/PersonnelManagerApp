package com.managerapp.personnelmanagerapp.di;


import androidx.annotation.NonNull;

import com.managerapp.personnelmanagerapp.data.remote.api.DecisionApiService;
import com.managerapp.personnelmanagerapp.utils.AuthInterceptor;
import com.managerapp.personnelmanagerapp.utils.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    private static final String BASE_URL = "http://10.251.4.5:8080/api/";

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(SecureTokenManager secureTokenManager) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new AuthInterceptor(secureTokenManager))
                .addInterceptor(loggingInterceptor)
                .build();
    }

    // Cung cấp Retrofit
    @Provides
    @Singleton
    public static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }


    @Provides
    @Singleton
    public static AuthApiService provideAuthApiService(Retrofit retrofit) {
        return retrofit.create(AuthApiService.class);
    }

    //User
    @Provides
    @Singleton
    public static UserApiService provideUserApiService(Retrofit retrofit) {
        return retrofit.create(UserApiService.class);
    }

    //
    @Provides
    @Singleton
    public static NotificationApiService provideNotificationApiService(Retrofit retrofit) {
        return retrofit.create(NotificationApiService.class);
    }

    @Provides
    @Singleton
    public static LeaveTypeApiService provideLeaveTypeApiService(Retrofit retrofit) {
        return retrofit.create(LeaveTypeApiService.class);
    }

    @NonNull
    @Provides
    @Singleton
    public static LeaveApplicationApiService leaveApplicationApiService (Retrofit retrofit) {
        return retrofit.create(LeaveApplicationApiService.class);
    }

    @NonNull
    @Provides
    @Singleton
    public static FeedbackApiService provideFeedbackApiService(Retrofit retrofit) {
        return retrofit.create(FeedbackApiService.class);
    }

    @Provides
    @Singleton
    public static DecisionApiService provideDecisionApiService(Retrofit retrofit) {
        return retrofit.create(DecisionApiService.class);
    }


    @Provides
    @Singleton
    public static ContractApiService provideContractApiService(Retrofit retrofit) {
        return retrofit.create(ContractApiService.class);
    }
}