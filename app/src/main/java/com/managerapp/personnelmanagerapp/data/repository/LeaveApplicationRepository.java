package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.LeaveApplicationApiService;
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
    public LeaveApplicationRepository(LeaveApplicationApiService leaveApplicationApiService) {
        this.leaveApplicationApiService = leaveApplicationApiService;
    }

    public Single<List<LeaveApplication>> getLeaveApplications() {
        return leaveApplicationApiService.getLeaveApplications()
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
}
