package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.usecase.GetAllContractsUseCase;
import com.managerapp.personnelmanagerapp.ui.state.ContractListState;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ContractListViewModel extends ViewModel {
    private final GetAllContractsUseCase getAllContractsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ContractListState> contractState = new MutableLiveData<>();

    @Inject
    public ContractListViewModel(GetAllContractsUseCase getAllContractsUseCase) {
        this.getAllContractsUseCase = getAllContractsUseCase;
    }

    public MutableLiveData<ContractListState> getContractState() {
        return contractState;
    }

    public void loadAllContracts(int userId) {
        contractState.postValue(new ContractListState.Loading());
        disposables.add(
                getAllContractsUseCase.execute(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( contracts -> {
                            if (contracts.isEmpty()) {
                                contractState.postValue(new ContractListState.Empty());
                            } else {
                                contractState.postValue(new ContractListState.Success(contracts));
                            }
                        },
                                throwable -> new ContractListState.Error(throwable.getMessage())
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear(); // Hủy tất cả request khi ViewModel bị hủy
    }
}
