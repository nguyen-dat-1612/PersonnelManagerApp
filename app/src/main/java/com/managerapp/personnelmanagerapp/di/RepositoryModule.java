package com.managerapp.personnelmanagerapp.di;

import com.managerapp.personnelmanagerapp.data.remote.api.DecisionApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.DepartmentApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FileApiService;
import com.managerapp.personnelmanagerapp.data.repository.DecisionRepository;
import com.managerapp.personnelmanagerapp.data.repository.DepartmentRepository;
import com.managerapp.personnelmanagerapp.data.repository.FileRepository;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;
import com.managerapp.personnelmanagerapp.utils.manager.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;
import com.managerapp.personnelmanagerapp.data.repository.ContractRepository;
import com.managerapp.personnelmanagerapp.data.repository.FeedBackRepository;
import com.managerapp.personnelmanagerapp.data.repository.LeaveApplicationRepository;
import com.managerapp.personnelmanagerapp.data.repository.LeaveTypeRepository;
import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.data.repository.UserRepository;
import com.managerapp.personnelmanagerapp.utils.manager.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public static AuthRepository provideAuthRepository(AuthApiService authApiService, SecureTokenManager secureTokenManager, SessionManager sessionManager) {
        return new AuthRepository(authApiService, secureTokenManager, sessionManager);
    }

    @Provides
    @Singleton
    public static UserRepository provideUserRepository(UserApiService userApiService, LocalDataManager localDataManager) {
        return new UserRepository(userApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static NotificationRepository provideNotificationRepository(NotificationApiService notificationApiService, LocalDataManager localDataManager) {
        return new NotificationRepository(notificationApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static LeaveTypeRepository provideLeaveTypeRepository(LeaveTypeApiService leaveTypeApiService) {
        return new LeaveTypeRepository(leaveTypeApiService);
    }

    @Provides
    @Singleton
    public static LeaveApplicationRepository leaveApplicationRepository(LeaveApplicationApiService leaveApplicationApiService, LocalDataManager localDataManager) {
        return new LeaveApplicationRepository(leaveApplicationApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static FeedBackRepository provideFeedBackRepository(FeedbackApiService feedbackApi) {
        return new FeedBackRepository(feedbackApi);
    }

    @Provides
    @Singleton
    public static DecisionRepository provideDecisionRepository(DecisionApiService decisionApiService, LocalDataManager localDataManager) {
        return new DecisionRepository(decisionApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static ContractRepository provideContractRepository(ContractApiService contractApi) {
        return new ContractRepository(contractApi);
    }

    @Provides
    public static FileRepository provideFileRepository(FileApiService fileApiService) {
        return new FileRepository(fileApiService);
    }

    @Provides
    public static DepartmentRepository provideDepartmentRepository(DepartmentApiService departmentApiService, LocalDataManager localDataManager) {
        return new DepartmentRepository(departmentApiService, localDataManager);
    }
}
