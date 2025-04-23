package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;
import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class LeaveApplicationRepository {
    private final LeaveApplicationApiService leaveApplicationApiService;
    private final String TAG = "LeaveApplicationRepository";

    @Inject
    public LeaveApplicationRepository(
            LeaveApplicationApiService leaveApplicationApiService
    ) {
        this.leaveApplicationApiService = leaveApplicationApiService;
    }

    public Single<List<LeaveApplicationResponse>> getLeaveApplications(int userId) {
        return leaveApplicationApiService.getLeaveApplications(userId)
                .flatMap(response-> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thành công");
                        return Single.just(response.body().getData());  // ✅ Sửa getData() -> getContracts()
                    } else {
                        String errorMessage = "Lấy dữ liệu thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<LeaveApplication> getLeaveApplicationById(String id) {
        return leaveApplicationApiService.getLeaveApplicationById(id)
                .flatMap(response-> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thành công");
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Lấy dữ liệu thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<LeaveApplicationResponse> createLeaveApplication(LeaveApplicationRequest leaveApplicationRequest) {
        return leaveApplicationApiService.createLeaveApplication(leaveApplicationRequest)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Tạo yêu cầu nghỉ phép thành công");
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Gửi yêu cầu xin nghỉ phép thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Gửi yêu cầu xin nghỉ phép thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                }
        );
    }

    public Single<List<LeaveApplicationResponse>> getApplicationIsPending() {
        return leaveApplicationApiService.getApplicationIsPending("PENDING")
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thành công");
                        Log.d(TAG, response.body().getData() + "");
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Lấy dữ liệu thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<BaseResponse<LeaveApplicationResponse>> confirmApplication(long applicationId, String formStatusEnum) {
        return leaveApplicationApiService.confirmApplication(applicationId, formStatusEnum)
                .flatMap(
                        response -> {
                            if (response.isSuccessful() && response.body() != null) {
                                Log.d(TAG, "Xác nhận yêu cầu nghỉ phép thành công");
                                return Single.just(response.body());
                            } else {
                                String errorMessage = "Xác nhận yêu cầu nghỉ phép thất bại: " + response.code();
                                if (response.errorBody() != null) {
                                    try {
                                        errorMessage = response.errorBody().string();
                                    } catch (Exception e) {
                                        Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                                    }
                                }
                                Log.d(TAG, "Xác nhận thất bại: " + errorMessage);
                                return Single.error(new Exception(errorMessage));
                            }
                        }
                );
    }
}
