package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;
import com.managerapp.personnelmanagerapp.data.remote.api.DisciplineAssignmentApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.BaseResponse;
import com.managerapp.personnelmanagerapp.domain.model.DisciplineAssignment;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class DisciplineAssignmentRepository {
    private final DisciplineAssignmentApiService disciplineAssignmentApiService;
    private static final String TAG = "DisciplineAssignmentRepo";

    @Inject
    public DisciplineAssignmentRepository(DisciplineAssignmentApiService disciplineAssignmentApiService) {
        this.disciplineAssignmentApiService = disciplineAssignmentApiService;
    }

    public Single<List<DisciplineAssignment>> getDisciplineAssignments(int userId) {
        return disciplineAssignmentApiService.getDisciplineAssignments(userId)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        return Single.just(response.body().getData());
                    } else {
                        return Single.error(new Exception("Lỗi lấy danh sách kỷ luật: " + response.body().getMessage()));
                    }
                })
                .doOnError(throwable -> Log.e(TAG, "Lỗi khi lấy danh sách kỷ luật: ", throwable));
    }

    public Single<DisciplineAssignment> getDisciplineAssignmentById(int userId, int disciplineId) {
        return disciplineAssignmentApiService.getDisciplineAssignmentById(userId, disciplineId)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        return Single.just(response.body().getData());
                    } else {
                        return Single.error(new Exception("Lỗi lấy chi tiết kỷ luật: " + response.body().getMessage()));
                    }
                })
                .doOnError(throwable -> Log.e(TAG, "Lỗi khi lấy chi tiết kỷ luật: ", throwable));
    }
}
