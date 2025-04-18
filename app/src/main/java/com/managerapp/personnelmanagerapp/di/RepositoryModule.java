package com.managerapp.personnelmanagerapp.di;

import com.managerapp.personnelmanagerapp.data.local.NotificationRecipientDao;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;
import com.managerapp.personnelmanagerapp.utils.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.api.AuthApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.DisciplineAssignmentApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.FeedbackApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.NotificationApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.RewardAssignmentApiService;
import com.managerapp.personnelmanagerapp.data.remote.api.UserApiService;
import com.managerapp.personnelmanagerapp.data.repository.AuthRepository;
import com.managerapp.personnelmanagerapp.data.repository.ContractRepository;
import com.managerapp.personnelmanagerapp.data.repository.DisciplineAssignmentRepository;
import com.managerapp.personnelmanagerapp.data.repository.FeedBackRepository;
import com.managerapp.personnelmanagerapp.data.repository.LeaveApplicationRepository;
import com.managerapp.personnelmanagerapp.data.repository.LeaveTypeRepository;
import com.managerapp.personnelmanagerapp.data.repository.NotificationRepository;
import com.managerapp.personnelmanagerapp.data.repository.RewardAssignmentRepository;
import com.managerapp.personnelmanagerapp.data.repository.UserRepository;

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
    public static AuthRepository provideAuthRepository(AuthApiService authApiService, SecureTokenManager secureTokenManager) {
        return new AuthRepository(authApiService, secureTokenManager);
    }

    @Provides
    @Singleton
    public static UserRepository provideUserRepository(UserApiService userApiService, LocalDataManager localDataManager) {
        return new UserRepository(userApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static NotificationRepository provideNotificationRepository(NotificationApiService notificationApiService, LocalDataManager localDataManager, NotificationRecipientDao notificationRecipientDao) {
        return new NotificationRepository(notificationApiService, localDataManager, notificationRecipientDao);
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
    public static RewardAssignmentRepository provideRewardRepository(RewardAssignmentApiService rewardAssignmentApiService) {
        return new RewardAssignmentRepository(rewardAssignmentApiService);
    }

    @Provides
    @Singleton
    public static DisciplineAssignmentRepository provideDisciplineRepository(DisciplineAssignmentApiService disciplineAssignmentApiService) {
        return new DisciplineAssignmentRepository(disciplineAssignmentApiService);
    }

    @Provides
    @Singleton
    public static ContractRepository provideContractRepository(ContractApiService contractApi) {
        return new ContractRepository(contractApi);
    }
}
