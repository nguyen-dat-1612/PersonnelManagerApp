package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.usecase.GetContractByIdUseCase;
import com.managerapp.personnelmanagerapp.ui.state.ContractDetailState;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ContractDetailViewModel extends ViewModel {

    private final GetContractByIdUseCase getContractByIdUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ContractDetailState> contractDetailState = new MutableLiveData<>();

    @Inject
    public ContractDetailViewModel(GetContractByIdUseCase getContractByIdUseCase) {
        this.getContractByIdUseCase = getContractByIdUseCase;
    }

    @NonNull
    public LiveData<ContractDetailState> getContractDetailState() {
        return contractDetailState;
    }

    public void loadContractById(@NonNull String contractId) {
        contractDetailState.postValue(ContractDetailState.Loading.getInstance());

        disposables.add(
                getContractByIdUseCase.execute(contractId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                contract -> {
                                    if (contract != null) {
                                        contractDetailState.postValue(new ContractDetailState.Success(contract));
                                    } else {
                                        contractDetailState.postValue(new ContractDetailState.Error("Contract not found"));
                                    }
                                },
                                throwable -> {
                                    String errorMessage = throwable.getMessage() != null
                                            ? throwable.getMessage()
                                            : "An unknown error occurred";
                                    contractDetailState.postValue(new ContractDetailState.Error(errorMessage));
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
