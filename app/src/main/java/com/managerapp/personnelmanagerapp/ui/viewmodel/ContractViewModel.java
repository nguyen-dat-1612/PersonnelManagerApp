package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.domain.usecase.GetAllContractsUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.GetContractByIdUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ContractViewModel extends ViewModel {

    private final GetAllContractsUseCase getAllContractsUseCase;
    private final GetContractByIdUseCase getContractByIdUseCase;
    private final MutableLiveData<List<Contract>> contracts = new MutableLiveData<>();


    @Inject
    public ContractViewModel(GetAllContractsUseCase getAllContractsUseCase, GetContractByIdUseCase getContractByIdUseCase) {
        this.getAllContractsUseCase = getAllContractsUseCase;
        this.getContractByIdUseCase = getContractByIdUseCase;
    }

    public LiveData<List<Contract>> getContracts() {
        return contracts;
    }

    public void loadAllContracts() {
        if (contracts.getValue() == null) { // Chỉ gọi nếu chưa có dữ liệu
            getAllContractsUseCase.execute()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            contracts -> this.contracts.setValue(contracts),
                            throwable -> {
                                // Xử lý lỗi (có thể dùng LiveData khác để thông báo lỗi)
                            }
                    );
        }
    }
    public void loadContractById(String contractId) {
        getContractByIdUseCase.execute(contractId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        contract -> {
                            List<Contract> currentList = contracts.getValue() != null ? contracts.getValue() : new ArrayList<>();
                            currentList.add(contract); // Thêm contract vào danh sách nếu cần
                            contracts.setValue(currentList);
                        },
                        throwable -> {
                            // Xử lý lỗi
                        }
                );
    }
}
