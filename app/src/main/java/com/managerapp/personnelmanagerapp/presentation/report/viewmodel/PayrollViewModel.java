package com.managerapp.personnelmanagerapp.presentation.report.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;
import com.managerapp.personnelmanagerapp.domain.usecase.report.GetPayrollUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PayrollViewModel extends ViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private final GetPayrollUseCase getPayrollUseCase;

    private final MutableLiveData<UiState<List<Payroll>>> payrollUiState = new MutableLiveData<>();

    @Inject
    public PayrollViewModel(GetPayrollUseCase getPayrollUseCase) {
        this.getPayrollUseCase = getPayrollUseCase;
    }

    public LiveData<UiState<List<Payroll>>> getPayrollUiState() {
        return payrollUiState;
    }

    public void getPayroll(String startDate, String endDate) {
        disposable.add(getPayrollUseCase.execute(startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {
                            payrollUiState.setValue(new UiState.Success(value));
                        },
                        throwable -> {
                            payrollUiState.setValue(new UiState.Error<>(throwable.getMessage()));
                        }
                )

        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
