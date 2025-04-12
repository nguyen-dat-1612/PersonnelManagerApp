package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.LeaveTypeApiService;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class LeaveTypeRepository {

    private final LeaveTypeApiService leaveTypeApiService;
    private final String TAG = "LeaveTypeRepository";

    @Inject
    public LeaveTypeRepository(LeaveTypeApiService leaveTypeApiService) {
        this.leaveTypeApiService = leaveTypeApiService;
    }

    public Single<List<LeaveType>> getAllLeaveTypes() {
        return leaveTypeApiService.getAllLeaveTypes()
                .flatMap(response -> {
                    Log.d(TAG, "Đang gọi API lấy loại nghỉ phép");
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu loại nghỉ phép từ API thành công");
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
                        Log.e(TAG, "Lấy dữ liệu loại biến thể thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }
}
