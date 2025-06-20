package com.managerapp.personnelmanagerapp.di;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.managerapp.personnelmanagerapp.data.remote.api.DecisionApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.DepartmentApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FileApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.JobGradeApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.PositionApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.ReportApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.SalaryPromotionApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.SeniorityAllowanceRuleApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.WorkLogResponse;
import com.managerapp.personnelmanagerapp.network.AuthInterceptor;
import com.managerapp.personnelmanagerapp.manager.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;
import com.managerapp.personnelmanagerapp.utils.WorkLogDeserializer;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.managerapp.personnelmanagerapp.BuildConfig;
@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    @BaseUrl
    public static String provideBaseUrl() {
        return BuildConfig.BASE_URL;
    }
    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(SecureTokenManager secureTokenManager,@ApplicationContext Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new AuthInterceptor(secureTokenManager, context))
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(@BaseUrl String baseUrl, OkHttpClient client) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WorkLogResponse.class, new WorkLogDeserializer())
                .create();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
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

    @Provides
    @Singleton
    public static FileApiService provideFileApiService(Retrofit retrofit) {
        return retrofit.create(FileApiService.class);
    }

    @Provides
    @Singleton
    public static DepartmentApiService provideDepartmentApiService(Retrofit retrofit) {
        return retrofit.create(DepartmentApiService.class);

    }

    @Singleton
    @Provides
    public static ReportApiService provideReportApiService(Retrofit retrofit) {
        return retrofit.create(ReportApiService.class);
    }

    @Singleton
    @Provides
    public static PositionApiService providePositionApiService(Retrofit retrofit) {
        return retrofit.create(PositionApiService.class);
    }

    @Singleton
    @Provides
    public static SeniorityAllowanceRuleApiService provideSeniorityAllowanceRuleApiService(Retrofit retrofit) {
        return retrofit.create(SeniorityAllowanceRuleApiService.class);

    }

    @Singleton
    @Provides
    public static SalaryPromotionApiService provideSalaryPromotionApiService(Retrofit retrofit) {
        return retrofit.create(SalaryPromotionApiService.class);
    }

    @Singleton
    @Provides
    public static JobGradeApiService provideJobGradeApiService(Retrofit retrofit) {
        return retrofit.create(JobGradeApiService.class);
    }
}