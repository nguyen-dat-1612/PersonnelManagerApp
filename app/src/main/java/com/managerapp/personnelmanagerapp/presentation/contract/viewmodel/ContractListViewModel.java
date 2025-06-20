package com.managerapp.personnelmanagerapp.presentation.contract.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.domain.usecase.contract.GetAllContractsUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.manager.LocalDataManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ContractListViewModel extends ViewModel {
    private final GetAllContractsUseCase getAllContractsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<List<Contract>>> uiState = new MutableLiveData<>();
    @Inject
    public ContractListViewModel(GetAllContractsUseCase getAllContractsUseCase) {
        this.getAllContractsUseCase = getAllContractsUseCase;
    }

    public MutableLiveData<UiState<List<Contract>>> getUiState() {
        return uiState;
    }

    public void loadAllContracts() {
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(getAllContractsUseCase.execute()
                        .timeout(10, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                contracts -> {
                                    uiState.postValue(new UiState.Success(contracts));
                                },
                                throwable -> {
                                    uiState.postValue(new UiState.Error(throwable.getMessage()));
                                }
                        )
        );
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
