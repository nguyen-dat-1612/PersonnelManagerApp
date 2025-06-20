package com.managerapp.personnelmanagerapp.presentation.contract.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.domain.usecase.contract.GetContractByIdUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ContractDetailViewModel extends ViewModel {

    private final GetContractByIdUseCase getContractByIdUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<Contract>> uiState = new MutableLiveData<>();

    @Inject
    public ContractDetailViewModel(GetContractByIdUseCase getContractByIdUseCase) {
        this.getContractByIdUseCase = getContractByIdUseCase;
    }

    public LiveData<UiState<Contract>> getUiState() {
        return uiState;
    }

    public void loadContractById(@NonNull int contractId) {
        uiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                getContractByIdUseCase.execute(contractId)
                        .timeout(5, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                contract -> { uiState.postValue(new UiState.Success(contract));
                                },
                                throwable -> { uiState.postValue(new UiState.Error(throwable.getMessage()));
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
