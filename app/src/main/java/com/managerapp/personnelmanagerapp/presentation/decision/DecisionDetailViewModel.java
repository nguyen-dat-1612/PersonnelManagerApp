package com.managerapp.personnelmanagerapp.presentation.decision;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.GetDecisionByIdUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

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
    private final MutableLiveData<UiState<DecisionResponse>> uiState = new MutableLiveData<>();

    @Inject
    public DecisionDetailViewModel(GetDecisionByIdUseCase getDecisionByIdUseCase) {
        this.getDecisionByIdUseCase = getDecisionByIdUseCase;
    }

    public MutableLiveData<UiState<DecisionResponse>> getUiState() {
        return uiState;
    }

    public void fetchDecisionById(String decisionId) {
        uiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                getDecisionByIdUseCase.execute(decisionId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .timeout(10, TimeUnit.SECONDS)
                        .subscribe(
                                 decisionResponse-> {
                                    if (decisionResponse != null) {
                                        uiState.postValue(new UiState.Success<>(decisionResponse));
                                    } else {
                                        uiState.postValue(new UiState.Error("Decision not found"));
                                    }
                                },
                                throwable -> {
                                    uiState.postValue(new UiState.Error(throwable.getMessage()));
                                }
                        )
        );
    }
}
