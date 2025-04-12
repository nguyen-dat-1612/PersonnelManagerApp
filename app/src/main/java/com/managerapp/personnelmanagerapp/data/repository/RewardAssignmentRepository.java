package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;
import com.managerapp.personnelmanagerapp.data.remote.api.RewardAssignmentApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.RewardAssignment;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class RewardAssignmentRepository {
    private final RewardAssignmentApiService rewardAssignmentApiService;
    private static final String TAG = "RewardAssignmentRepo";

    @Inject
    public RewardAssignmentRepository(RewardAssignmentApiService rewardAssignmentApiService) {
        this.rewardAssignmentApiService = rewardAssignmentApiService;
    }

    public Single<List<AssignmentResponse>> getRewardAssignments(int userId) {

        return rewardAssignmentApiService.getRewardAssignments(userId)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, userId + "");
                        Log.d(TAG, "Lấy danh sách khen thưởng của người dùng");
                        Log.d(TAG, response.body().getData().toString());
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
                        Log.d(TAG, "Đăng nhập thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<AssignmentResponse> getRewardAssignmentById(int userId, int rewardId) {
        return rewardAssignmentApiService.getRewardAssignmentById(userId, rewardId)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        return Single.just(response.body().getData());
                    } else {
                        return Single.error(new Exception("Lỗi lấy chi tiết khen thưởng: " + response.body().getMessage()));
                    }
                })
                .doOnError(throwable -> Log.e(TAG, "Lỗi khi lấy chi tiết khen thưởng: ", throwable));
    }
}
