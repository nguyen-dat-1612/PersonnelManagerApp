package com.managerapp.personnelmanagerapp.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.managerapp.personnelmanagerapp.utils.manager.EncryptionManager;
import com.managerapp.personnelmanagerapp.utils.manager.SecureTokenManager;
import com.managerapp.personnelmanagerapp.data.remote.request.TokenRefreshRequest;
import com.managerapp.personnelmanagerapp.utils.manager.SessionManager;
import com.managerapp.personnelmanagerapp.utils.manager.SettingsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

// <editor-fold desc="📌 NOTE CHO TEAM - Giải thích StorageModule (quản lý lưu trữ, session, token...)">
/*
📦 StorageModule cung cấp các dependency liên quan đến lưu trữ cục bộ, token, và session cho toàn bộ ứng dụng.

🔐 Thành phần chính:
- `SharedPreferences`: dùng để lưu local data.
- `EncryptionManager`: dùng để mã hóa/giải mã dữ liệu (ví dụ token).
- `SecureTokenManager`: quản lý token có mã hóa.
- `SessionManager`: quản lý session đăng nhập và trạng thái người dùng.
- `SettingsManager`: quản lý cài đặt ứng dụng (tùy chỉnh UI, v.v).
- `TokenRefreshRequest`: object mẫu dùng cho việc refresh token khi cần.

🧩 Lưu ý:
- Tất cả đều được đánh dấu `@Singleton` → giữ một instance duy nhất trong suốt vòng đời ứng dụng.
- SharedPreferences được đặt tên là `"app_prefs"` – nếu muốn tách biệt giữa các module, có thể đổi tên riêng cho từng phần.

📌 Mở rộng:
- Nếu muốn thêm lớp quản lý cấu hình nào mới (ví dụ: AppThemeManager), có thể thêm tương tự như `SettingsManager`.
- Tránh logic nặng trong các lớp manager – nên tách riêng nếu phức tạp.

📁 Vị trí liên quan:
- Các class được inject từ module này thường nằm trong `utils/manager` hoặc được gọi từ Repository/ViewModel.
*/
// </editor-fold>

@Module
@InstallIn(SingletonComponent.class)
public class StorageModule {

    @Provides
    @Singleton
    public static EncryptionManager provideEncryptionManager() {
        return new EncryptionManager();
    }

    @Provides
    @Singleton
    public static SecureTokenManager provideSecureTokenManager(EncryptionManager encryptionManager, SharedPreferences sharedPreferences) {
        return new SecureTokenManager(encryptionManager, sharedPreferences);
    }

    @Provides
    @Singleton
    public static SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public static TokenRefreshRequest provideTokenRefreshRequest() {
        return new TokenRefreshRequest();
    }

    @Provides
    @Singleton
    public static SessionManager provideSessionManager(@ApplicationContext Context context) {
        return new SessionManager(context);
    }

    @Provides
    @Singleton
    public static SettingsManager provideSettingsManager(@ApplicationContext Context context) {
        return new SettingsManager(context);
    }
}
