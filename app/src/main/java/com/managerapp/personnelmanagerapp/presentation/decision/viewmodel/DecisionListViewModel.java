package com.managerapp.personnelmanagerapp.presentation.decision.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.GetDecisionsUseCase;
import com.managerapp.personnelmanagerapp.presentation.decision.state.DecisionListUiState;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DecisionListViewModel extends ViewModel {
    private final GetDecisionsUseCase getDecisionsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<DecisionListUiState> uiState = new MutableLiveData<>();

    @Inject
    public DecisionListViewModel(GetDecisionsUseCase getDecisionsUseCase) {
        this.getDecisionsUseCase = getDecisionsUseCase;
        fetchDecisions();
    }

    public MutableLiveData<DecisionListUiState> getUiState() {
        return uiState;
    }

    public void refreshDecisions() {
        fetchDecisions();
    }

    private void fetchDecisions() {
        uiState.setValue(DecisionListUiState.loading());
        disposables.add(getDecisionsUseCase.execute()
                        .timeout(10, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                decisions -> {
                                    uiState.setValue(DecisionListUiState.success(decisions));
                                },
                                throwable -> {
                                    uiState.setValue(DecisionListUiState.error(throwable.getMessage()));
                                }
                        )
        );
    }
}
