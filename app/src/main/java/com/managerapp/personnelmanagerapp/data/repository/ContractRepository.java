package com.managerapp.personnelmanagerapp.data.repository;

import android.util.Log;

import com.managerapp.personnelmanagerapp.data.remote.api.ContractApiService;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public class ContractRepository {
    private final ContractApiService contractApiService;
    private final String TAG = "ContractRepository";

    @Inject
    public ContractRepository(ContractApiService contractApiService) {
        this.contractApiService = contractApiService;
    }

    public Single<List<Contract>> getContracts(int userId) {
        return contractApiService.getContracts(userId)
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
                                Log.e(TAG, "Không thể đọc lỗi từ phản hồi", e);
                            }
                        }
                        Log.d(TAG, "Lấy dữ liệu thất bại: " + errorMessage);
                        return Single.error(new Exception(errorMessage));
                    }
                });
    }

    public Single<Contract> getContractByID(String contractId) {
        return contractApiService.getContractById(contractId)
                .flatMap(response -> {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Lấy dữ liệu thành công");
                        return Single.just(response.body().getData()); // ✅ Lấy contract đầu tiên
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
