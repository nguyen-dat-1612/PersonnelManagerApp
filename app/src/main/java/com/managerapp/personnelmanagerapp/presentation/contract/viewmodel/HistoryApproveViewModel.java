package com.managerapp.personnelmanagerapp.presentation.contract.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion.GetSalaryPromotionBySignerIdUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HistoryApproveViewModel extends ViewModel {
    private final GetSalaryPromotionBySignerIdUseCase getSalaryPromotionBySignerIdUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<UiState<List<SalaryPromotion>>> salaryPromotionsUiState = new MutableLiveData<>();

    @Inject
    public HistoryApproveViewModel(GetSalaryPromotionBySignerIdUseCase getSalaryPromotionBySignerIdUseCase) {
        this.getSalaryPromotionBySignerIdUseCase = getSalaryPromotionBySignerIdUseCase;
    }

    public MutableLiveData<UiState<List<SalaryPromotion>>> getSalaryPromotionsUiState() {
        return salaryPromotionsUiState;
    }

    public void getSalaryPromotions(FormStatusEnum formStatus) {
        salaryPromotionsUiState.setValue(UiState.Loading.getInstance());

        disposable.add(getSalaryPromotionBySignerIdUseCase.execute(formStatus)
                .subscribeOn(Schedulers.io())
                .timeout(5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        salaryPromotions -> {
                            salaryPromotionsUiState.setValue(new UiState.Success<>(salaryPromotions));
                        },
                        throwable -> salaryPromotionsUiState.setValue(new UiState.Error(throwable.getMessage()))
                )
        );
    }
}
