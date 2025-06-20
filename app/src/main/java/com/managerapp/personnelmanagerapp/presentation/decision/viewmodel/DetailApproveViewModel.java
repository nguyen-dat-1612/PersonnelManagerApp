package com.managerapp.personnelmanagerapp.presentation.decision.viewmodel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionApproveRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.DeleteDecisionUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.GetDecisionByIdUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.UpdateDecisionUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.file.UploadPdfUseCase;
import com.managerapp.personnelmanagerapp.presentation.decision.state.DecisionDetailUiState;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;

@HiltViewModel
public class DetailApproveViewModel extends ViewModel {
    private final GetDecisionByIdUseCase getDecisionByIdUseCase;
    private final UpdateDecisionUseCase updateDecisionUseCase;
    private final DeleteDecisionUseCase deleteDecisionUseCase;
    private final UploadPdfUseCase uploadPdfUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<Decision>> uiState = new MutableLiveData<>();
    private final MutableLiveData<UiState<Decision>> updateUiState = new MutableLiveData<>();
    private final MutableLiveData<UiState<Boolean>> deleteUiState = new MutableLiveData<>();

    private final MutableLiveData<UiState<String>> uploadResult = new MutableLiveData<>();

    public LiveData<UiState<String>> getUploadResult() {
        return uploadResult;
    }

    private final String decisionId;
    @Inject
    public DetailApproveViewModel(
            GetDecisionByIdUseCase getDecisionByIdUseCase, UpdateDecisionUseCase updateDecisionUseCase, DeleteDecisionUseCase deleteDecisionUseCase, UploadPdfUseCase uploadPdfUseCase,
            SavedStateHandle savedStateHandle
    ) {
        this.getDecisionByIdUseCase = getDecisionByIdUseCase;
        this.updateDecisionUseCase = updateDecisionUseCase;
        this.deleteDecisionUseCase = deleteDecisionUseCase;
        this.uploadPdfUseCase = uploadPdfUseCase;
        this.decisionId = savedStateHandle.get("decision_id");
        if (decisionId != null) {
            fetchDecisionById(decisionId);
        } else {
            uiState.setValue(new UiState.Error<>("Không có ID quyết định"));
        }
    }

    public void refresh() {
        fetchDecisionById(decisionId);
    }

    public LiveData<UiState<Decision>> getUiState() {
        return uiState;
    }

    public void fetchDecisionById(String decisionId) {

        if (decisionId == null || decisionId.isEmpty()) {
            uiState.setValue(new UiState.Error<>("ID quyết định không hợp lệ"));
            return;
        }

        uiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                getDecisionByIdUseCase.execute(decisionId)
                        .subscribeOn(Schedulers.io())
                        .timeout(10, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                decisionResponse-> {
                                    if (decisionResponse != null) {
                                        uiState.setValue(new UiState.Success<>(decisionResponse));
                                    } else {
                                        uiState.setValue(new UiState.Error<>("Không tìm thấy dữ liệu"));
                                    }
                                },
                                throwable -> {
                                    uiState.setValue(new UiState.Error<>(throwable.getMessage()));
                                }
                        )
        );
    }

    public LiveData<UiState<Decision>> getUpdateUiState() {
        return updateUiState;
    }

    public void updateDecision(String decisionId, String attachment) {
        updateUiState.setValue(UiState.Loading.getInstance());

        disposables.add(
                updateDecisionUseCase.execute(decisionId, attachment)
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

    public void uploadFile(MultipartBody.Part file) {
        uploadResult.setValue(UiState.Loading.getInstance());
        disposables.add(
                uploadPdfUseCase.execute(file)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                link -> uploadResult.setValue(new UiState.Success(link)),
                                error -> uploadResult.setValue(new UiState.Error(error.getMessage()))
                        )
        );
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
