package com.managerapp.personnelmanagerapp.presentation.decision.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.decision.GetDecisionByIdUseCase;
import com.managerapp.personnelmanagerapp.presentation.decision.state.DecisionDetailUiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DecisionDetailViewModel extends ViewModel {
    private final GetDecisionByIdUseCase getDecisionByIdUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<DecisionDetailUiState> uiState = new MutableLiveData<>();
    private final String decisionId;
    @Inject
    public DecisionDetailViewModel(
            GetDecisionByIdUseCase getDecisionByIdUseCase,
            SavedStateHandle savedStateHandle
    ) {
        this.getDecisionByIdUseCase = getDecisionByIdUseCase;
        this.decisionId = savedStateHandle.get("decision_id");
        if (decisionId != null) {
            fetchDecisionById(decisionId);
        } else {
            uiState.setValue(DecisionDetailUiState.error("Không có ID quyết định"));
        }
    }

    public void refresh() {
        fetchDecisionById(decisionId);
    }

    public MutableLiveData<DecisionDetailUiState> getUiState() {
        return uiState;
    }

    public void fetchDecisionById(String decisionId) {

        if (decisionId == null || decisionId.isEmpty()) {
            uiState.setValue(DecisionDetailUiState.error("ID quyết định không hợp lệ"));
            return;
        }

        uiState.setValue(DecisionDetailUiState.loading());

        disposables.add(
                getDecisionByIdUseCase.execute(decisionId)
                        .subscribeOn(Schedulers.io())
                        .timeout(10, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                 decisionResponse-> {
                                    if (decisionResponse != null) {
                                        uiState.setValue(DecisionDetailUiState.success(decisionResponse));
                                    } else {
                                        uiState.setValue(DecisionDetailUiState.error("Không tìm thấy dữ liệu"));
                                    }
                                },
                                throwable -> {
                                    uiState.setValue(DecisionDetailUiState.error(throwable.getMessage()));
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
