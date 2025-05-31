package com.managerapp.personnelmanagerapp.di;

import com.managerapp.personnelmanagerapp.data.remote.api.*;
import com.managerapp.personnelmanagerapp.data.repository.*;
import com.managerapp.personnelmanagerapp.domain.repository.*;
import com.managerapp.personnelmanagerapp.utils.manager.LocalDataManager;
import com.managerapp.personnelmanagerapp.utils.manager.SecureTokenManager;
import com.managerapp.personnelmanagerapp.utils.manager.SessionManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

// <editor-fold desc="📌 NOTE CHO TEAM - Giải thích RepositoryModule">
/*
🗂️ RepositoryModule chịu trách nhiệm cung cấp các instance Repository dùng trong toàn app.

🎯 Mục đích:
- Gắn các interface `Repository` với các `RepositoryImpl` cụ thể.
- Giúp ViewModel hoặc các lớp khác có thể inject Repository thông qua Hilt (Dagger).

🔗 Cấu trúc:
- Mỗi Repository cần thiết thường sử dụng 1 hoặc nhiều ApiService (Retrofit) + LocalDataManager/SecureTokenManager/SessionManager.

📌 Hướng dẫn mở rộng:
1. Tạo Repository interface trong `domain.repository`.
2. Tạo `RepositoryImpl` trong `data.repository`.
3. Viết hàm `@Provides` tương ứng ở đây, sử dụng các API service hoặc manager cần thiết.
4. Đảm bảo thêm `@Singleton` nếu bạn muốn giữ một instance duy nhất toàn app.

💡 Lưu ý:
- Module này được `@InstallIn(SingletonComponent.class)`, nghĩa là các dependencies được inject có vòng đời toàn ứng dụng.
- Thứ tự các `@Provides` nên đi theo nhóm logic để dễ đọc: User, Leave, File, etc.
*/
// </editor-fold>

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public static AuthRepository provideAuthRepository(
            AuthApiService authApiService,
            SecureTokenManager secureTokenManager,
            SessionManager sessionManager
    ) {
        return new AuthRepositoryImpl(authApiService, secureTokenManager, sessionManager);
    }

    @Provides
    @Singleton
    public static UserRepository provideUserRepository(
            UserApiService userApiService,
            LocalDataManager localDataManager
    ) {
        return new UserRepositoryImpl(userApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static NotificationRepository provideNotificationRepository(
            NotificationApiService notificationApiService,
            LocalDataManager localDataManager
    ) {
        return new NotificationRepositoryImpl(notificationApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static LeaveTypeRepository provideLeaveTypeRepository(
            LeaveTypeApiService leaveTypeApiService
    ) {
        return new LeaveTypeRepositoryImpl(leaveTypeApiService);
    }

    @Provides
    @Singleton
    public static LeaveApplicationRepository provideLeaveApplicationRepository(
            LeaveApplicationApiService leaveApplicationApiService,
            LocalDataManager localDataManager
    ) {
        return new LeaveApplicationRepositoryImpl(leaveApplicationApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static FeedBackRepository provideFeedBackRepository(
            FeedbackApiService feedbackApi
    ) {
        return new FeedBackRepositoryImpl(feedbackApi);
    }

    @Provides
    @Singleton
    public static DecisionRepository provideDecisionRepository(
            DecisionApiService decisionApiService,
            LocalDataManager localDataManager
    ) {
        return new DecisionRepositoryImpl(decisionApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static ContractRepository provideContractRepository(
            ContractApiService contractApi
    ) {
        return new ContractRepositoryImpl(contractApi);
    }

    @Provides
    @Singleton
    public static FileRepository provideFileRepository(
            FileApiService fileApiService
    ) {
        return new FileRepositoryImpl(fileApiService);
    }

    @Provides
    @Singleton
    public static DepartmentRepository provideDepartmentRepository(
            DepartmentApiService departmentApiService,
            LocalDataManager localDataManager
    ) {
        return new DepartmentRepositoryImpl(departmentApiService, localDataManager);
    }

    @Provides
    @Singleton
    public static ReportRepository provideReportRepository(
            ReportApiService reportApiService
    ) {
        return new ReportRepositoryImpl(reportApiService);
    }
}
