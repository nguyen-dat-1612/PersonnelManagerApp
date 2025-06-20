package com.managerapp.personnelmanagerapp.presentation.decision.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.GetAllDecisionUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ApproveDecisionViewModel extends ViewModel {
    private final GetAllDecisionUseCase getAllDecisionUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<UiState<List<Decision>>> getDecisionUiState= new MutableLiveData<>();
    private final MutableLiveData<DecisionEnum> selectedType = new MutableLiveData<>();
    private final MutableLiveData<Boolean> selectedStatus = new MutableLiveData<>();

    public void setSelectedType(DecisionEnum type) {
        selectedType.setValue(type);
        fetchIfReady();
    }

    public void setSelectedStatus(boolean approved) {
        selectedStatus.setValue(approved);
        fetchIfReady();
    }

    private void fetchIfReady() {
        DecisionEnum type = selectedType.getValue();
        Boolean status = selectedStatus.getValue();
        if (type != null && status != null) {
            getAllDecisions(type.name(), status);
        }
    }

    public LiveData<UiState<List<Decision>>> getGetDecisionUiState() {
        return getDecisionUiState;
    }

    @Inject
    public ApproveDecisionViewModel(GetAllDecisionUseCase getAllDecisionUseCase) {
        this.getAllDecisionUseCase = getAllDecisionUseCase;
    }

    public void getAllDecisions(String type, Boolean approve) {
        getDecisionUiState.setValue(UiState.Loading.getInstance());

        compositeDisposable.add(getAllDecisionUseCase.execute(type)
                .flatMapIterable(list -> list)
                .filter(decision -> {
                    if (approve == null) return true;
                    return approve ? decision.getSigner() != null : decision.getSigner() == null;
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(decisions -> {
                    getDecisionUiState.setValue(new UiState.Success(decisions));
                }, throwable -> {
                    getDecisionUiState.setValue(new UiState.Error(throwable.getMessage()));
                })
        );
    }

}
