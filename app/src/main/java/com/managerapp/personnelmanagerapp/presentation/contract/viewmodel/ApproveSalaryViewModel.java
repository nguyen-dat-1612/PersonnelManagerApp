package com.managerapp.personnelmanagerapp.presentation.contract.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion.DeleteSalaryPromotionUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion.GetSalaryPromotionPendingUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion.UpdateSalaryPromotionUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.text.Normalizer;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ApproveSalaryViewModel extends ViewModel {
    private final GetSalaryPromotionPendingUseCase getSalaryPromotionPendingUseCase;
    private final UpdateSalaryPromotionUseCase updateSalaryPromotionUseCase;
    private final DeleteSalaryPromotionUseCase deleteSalaryPromotionUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<UiState<List<SalaryPromotion>>> salaryPromotionUiState = new MutableLiveData<>();
    private MutableLiveData<UiState<SalaryPromotion>> updateSalaryPromotionUiState = new MutableLiveData<>();

    public LiveData<UiState<List<SalaryPromotion>>> getSalaryPromotionUiState() {
        return salaryPromotionUiState;
    }
    public LiveData<UiState<SalaryPromotion>> getUpdateSalaryPromotionUiState() {
        return updateSalaryPromotionUiState;
    }

    @Inject
    public ApproveSalaryViewModel(GetSalaryPromotionPendingUseCase getSalaryPromotionPendingUseCase, UpdateSalaryPromotionUseCase updateSalaryPromotionUseCase, DeleteSalaryPromotionUseCase deleteSalaryPromotionUseCase) {
        this.getSalaryPromotionPendingUseCase = getSalaryPromotionPendingUseCase;
        this.updateSalaryPromotionUseCase = updateSalaryPromotionUseCase;
        this.deleteSalaryPromotionUseCase = deleteSalaryPromotionUseCase;
        loadSalaryPromotions();
    }

    public void loadSalaryPromotions() {
        salaryPromotionUiState.setValue(UiState.Loading.getInstance());
        disposable.add(getSalaryPromotionPendingUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        salaryPromotions -> {
                            salaryPromotionUiState.setValue(new UiState.Success<>(salaryPromotions));
                        },
                        throwable -> {
                            salaryPromotionUiState.setValue(new UiState.Error(throwable.getMessage()));
                        }
                )
        );
    }

    public void approveSalaryPromotion(Integer id, FormStatusEnum formStatusEnum, String note) {
        updateSalaryPromotionUiState.setValue(UiState.Loading.getInstance());
        disposable.add(updateSalaryPromotionUseCase.execute(id,formStatusEnum, note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        updatedSalaryPromotion -> {
                            updateSalaryPromotionUiState.setValue(new UiState.Success(updatedSalaryPromotion));
                            loadSalaryPromotions();
                        },
                        throwable -> {
                            updateSalaryPromotionUiState.setValue(new UiState.Error(throwable.getMessage()));
                        }
                )
        );
    }
}
