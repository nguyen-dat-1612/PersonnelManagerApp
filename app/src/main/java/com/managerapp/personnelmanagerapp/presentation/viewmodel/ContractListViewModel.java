package com.managerapp.personnelmanagerapp.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.GetAllContractsUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;

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
    private final MutableLiveData<UiState<List<ContractResponse>>> uiState = new MutableLiveData<>();
    private final LocalDataManager localDataManager;
    @Inject
    public ContractListViewModel(GetAllContractsUseCase getAllContractsUseCase, LocalDataManager localDataManager) {
        this.getAllContractsUseCase = getAllContractsUseCase;
        this.localDataManager = localDataManager;
    }

    public MutableLiveData<UiState<List<ContractResponse>>> getUiState() {
        return uiState;
    }

    public void loadAllContracts() {
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(
                Single.fromCallable(() -> Long.parseLong(localDataManager.getUserId()))
                        .flatMap(userId -> {
                            if (userId <= 0) {
                                return Single.error(new Throwable("Invalid user ID"));
                            }
                            return getAllContractsUseCase.execute(userId);
                        })
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
        disposables.clear(); // Hủy tất cả request khi ViewModel bị hủy
    }
}
