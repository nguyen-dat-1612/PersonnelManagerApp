package com.managerapp.personnelmanagerapp.presentation.decision.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionApproveRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.DeleteDecisionUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.GetDecisionByIdUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.UpdateDecisionUseCase;
import com.managerapp.personnelmanagerapp.presentation.decision.state.DecisionDetailUiState;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DetailApproveViewModel extends ViewModel {
    private final GetDecisionByIdUseCase getDecisionByIdUseCase;
    private final UpdateDecisionUseCase updateDecisionUseCase;
    private final DeleteDecisionUseCase deleteDecisionUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<DecisionDetailUiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<UiState<DecisionResponse>> updateUiState = new MutableLiveData<>();
    private final MutableLiveData<UiState<Boolean>> deleteUiState = new MutableLiveData<>();
    private final String decisionId;
    @Inject
    public DetailApproveViewModel(
            GetDecisionByIdUseCase getDecisionByIdUseCase, UpdateDecisionUseCase updateDecisionUseCase, DeleteDecisionUseCase deleteDecisionUseCase,
            SavedStateHandle savedStateHandle
    ) {
        this.getDecisionByIdUseCase = getDecisionByIdUseCase;
        this.updateDecisionUseCase = updateDecisionUseCase;
        this.deleteDecisionUseCase = deleteDecisionUseCase;
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

    public MutableLiveData<UiState<DecisionResponse>> getUpdateUiState() {
        return updateUiState;
    }

    public void updateDecision(String decisionId) {
        updateUiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                updateDecisionUseCase.execute(decisionId)
                        .subscribeOn(Schedulers.io())
                        .timeout(10, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                decisionResponse -> {
                                    updateUiState.setValue(new UiState.Success(decisionResponse));
                                },
                                throwable -> {
                                    updateUiState.setValue(new UiState.Error(throwable.getMessage()));
                                }
                        )
        );
    }

    public MutableLiveData<UiState<Boolean>> getDeleteUiState() {
        return deleteUiState;
    }

    public void deleteDecision(String decisionId) {
        deleteUiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                deleteDecisionUseCase.execute(decisionId)
                        .subscribeOn(Schedulers.io())
                        .timeout(10, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                success -> {
                                    deleteUiState.setValue(new UiState.Success(success));
                                },
                                throwable -> {
                                    deleteUiState.setValue(new UiState.Error(throwable.getMessage()));
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
