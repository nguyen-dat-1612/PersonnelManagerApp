package com.managerapp.personnelmanagerapp.presentation.report.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.usecase.report.GetContractExpireReportUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ContractExpireViewModel extends ViewModel {
    private final GetContractExpireReportUseCase getContractExpireReportUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<UiState<List<ContractExpireReport>>> contractExpireUiState = new MutableLiveData<>();

    private final MutableLiveData<Integer> selectedDays = new MutableLiveData<>(30);
    public LiveData<Integer> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(int days) {
        selectedDays.setValue(days);
    }

    @Inject
    public ContractExpireViewModel(GetContractExpireReportUseCase getContractExpireReportUseCase) {
        this.getContractExpireReportUseCase = getContractExpireReportUseCase;
    }

    public LiveData<UiState<List<ContractExpireReport>>> getContractExpireUiState() {
        return contractExpireUiState;
    }

    public void loadContractsFilteredBy(int days) {
        disposable.add(
                getContractExpireReportUseCase.execute(days)
                        .subscribeOn(Schedulers.io())
                        .timeout(5, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                value -> {
                                    contractExpireUiState.setValue(new UiState.Success(value));
                                },
                                throwable -> {
                                    contractExpireUiState.setValue(new UiState.Error<>(throwable.getMessage()));
                                }
                        ));
    }

    public void exportExcel(Context context, int days) {

    }

    public void exportPdfReport(Context context, int days)  {

    }
}
