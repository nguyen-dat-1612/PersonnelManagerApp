package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.DecisionApiService;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class DecisionRepository {
    private final String TAG = "DecisionRepository";

    private final DecisionApiService decisionApiService;

    @Inject
    public DecisionRepository(DecisionApiService decisionApiService) {
        this.decisionApiService = decisionApiService;
    }

    public Single<List<DecisionResponse>> getDecisions(long userId) {
        return decisionApiService.getAllDecisionsByUserId(userId)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thành công");
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Lấy dữ liệu thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {
                            }
                        }
                        Log.e(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<DecisionResponse> getDecisionById(String id) {
        return decisionApiService.getDecisionById(id)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        return Single.just(response.body().getData());
                    } else {
                        String errorMessage = "Lấy dữ liệu thất bại: " + response.code();
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (Exception e) {

                            }
                        }
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }
}
