package com.managerapp.personnelmanagerapp.presentation.decision;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.GetDecisionsUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class DecisionListViewModel extends ViewModel {
    private final GetDecisionsUseCase getDecisionsUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<List<DecisionResponse>>> uiState = new MutableLiveData<>();
    private final LocalDataManager localDataManager;

    @Inject
    public DecisionListViewModel(GetDecisionsUseCase getDecisionsUseCase, LocalDataManager localDataManager) {
        this.getDecisionsUseCase = getDecisionsUseCase;
        this.localDataManager = localDataManager;
    }

    public LiveData<UiState<List<DecisionResponse>>> getUiState() {
        return uiState;
    }

    public void fetchDecisions() {
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(
                Single.fromCallable(() -> Long.parseLong(localDataManager.getUserId()))
                        .flatMap(userId -> {
                            if (userId <= 0) {
                                return Single.error(new Throwable("Invalid user ID"));
                            }
                            return getDecisionsUseCase.execute(userId);
                        })
                        .timeout(10, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                decisions -> {
                                    uiState.postValue(new UiState.Success(decisions));
                                },
                                throwable -> {
                                    uiState.postValue(new UiState.Error(throwable.getMessage()));
                                }
                        )
        );
    }
}
