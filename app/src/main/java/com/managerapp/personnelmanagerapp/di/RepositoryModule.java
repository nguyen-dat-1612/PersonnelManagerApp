package com.managerapp.personnelmanagerapp.di;

import com.managerapp.personnelmanagerapp.data.repository.*;
import com.managerapp.personnelmanagerapp.domain.repository.*;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract AuthRepository bindAuthRepository(AuthRepositoryImpl impl);

    @Binds
    @Singleton
    abstract UserRepository bindUserRepository(UserRepositoryImpl impl);

    @Binds
    @Singleton
    abstract NotificationRepository bindNotificationRepository(NotificationRepositoryImpl impl);

    @Binds
    @Singleton
    abstract LeaveTypeRepository bindLeaveTypeRepository(LeaveTypeRepositoryImpl impl);

    @Binds
    @Singleton
    abstract LeaveApplicationRepository bindLeaveApplicationRepository(LeaveApplicationRepositoryImpl impl);

    @Binds
    @Singleton
    abstract FeedBackRepository bindFeedBackRepository(FeedBackRepositoryImpl impl);

    @Binds
    @Singleton
    abstract DecisionRepository bindDecisionRepository(DecisionRepositoryImpl impl);

    @Binds
    @Singleton
    abstract ContractRepository bindContractRepository(ContractRepositoryImpl impl);

    @Binds
    @Singleton
    abstract FileRepository bindFileRepository(FileRepositoryImpl impl);

    @Binds
    @Singleton
    abstract DepartmentRepository bindDepartmentRepository(DepartmentRepositoryImpl impl);

    @Binds
    @Singleton
    abstract ReportRepository bindReportRepository(ReportRepositoryImpl impl);

    @Binds
    @Singleton
    abstract PositionRepository bindPositionRepository(PositionRepositoryImpl impl);

    @Binds
    @Singleton
    abstract SeniorityAllowanceRuleRepository bindSeniorityAllowanceRuleRepository(SeniorityAllowanceRuleRepositoryImpl impl);

    @Binds
    @Singleton
    abstract SalaryPromotionRepository bindSalaryPromotionRepository(SalaryPromotionRepositoryImpl impl);

    @Binds
    @Singleton
    abstract JobGradeRepository bindJobGradeRepository(JobGradeRepositoryImpl impl);
}
