package com.managerapp.personnelmanagerapp.presentation.viewmodel;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.domain.usecase.GetRewardAssignmentByIdUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.GetRewardAssignmentsUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class RewardViewModel extends ViewModel {
    private final GetRewardAssignmentsUseCase getRewardAssignmentsUseCase;
    private final GetRewardAssignmentByIdUseCase getRewardAssignmentByIdUseCase;
    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<UiState<List<AssignmentResponse>>> uiState = new MutableLiveData<>();

    @Inject
    public RewardViewModel(GetRewardAssignmentsUseCase getRewardAssignmentsUseCase, GetRewardAssignmentByIdUseCase getRewardAssignmentByIdUseCase) {
        this.getRewardAssignmentsUseCase = getRewardAssignmentsUseCase;
        this.getRewardAssignmentByIdUseCase = getRewardAssignmentByIdUseCase;
    }

    public MutableLiveData<UiState<List<AssignmentResponse>>> getUiState() {
        return uiState;
    }

    public void loadAllRewards(int userId) {
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(
                getRewardAssignmentsUseCase.execute(userId)
                        .timeout(10, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( response -> {
                                    if (response.isEmpty()) {
                                        uiState.postValue(new UiState.Error("Danh sách khen thưởng trống"));
                                    } else {
                                        uiState.postValue(new UiState.Success<>(response));
                                    }
                                },
                                throwable -> {
                                    uiState.postValue(new UiState.Error("Đã có lỗi xảy ra: " + throwable.getMessage()));
                                }
                        )

        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposables != null) {
            disposables.dispose();
        }
    }
    private final String TAG = "RewardViewModel";
}
