package com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel;

import android.util.Pair;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.CreateLeaveAppUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.GetAllLeaveTypeUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.state.CreateLeaveUiState;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class CreateLeaveViewModel extends ViewModel {
    private final GetAllLeaveTypeUseCase getAllLeaveTypeUseCase;
    private final CreateLeaveAppUseCase createLeaveAppUseCase;
    private final GetUserUseCase getUserProfileUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<CreateLeaveUiState> uiState = new MutableLiveData<>();

    private List<LeaveType> cachedLeaveTypes;
    private UserProfileResponse cachedUserProfile;
    private Integer leaveTypeId;

    private Long userId;

    @Inject
    public CreateLeaveViewModel(GetAllLeaveTypeUseCase getAllLeaveTypeUseCase,
                                CreateLeaveAppUseCase createLeaveAppUseCase,
                                GetUserUseCase getUserProfileUseCase) {
        this.getAllLeaveTypeUseCase = getAllLeaveTypeUseCase;
        this.createLeaveAppUseCase = createLeaveAppUseCase;
        this.getUserProfileUseCase = getUserProfileUseCase;
        loadInitialData();
    }

    public LiveData<CreateLeaveUiState> getUiState() {
        return uiState;
    }

    public void loadInitialData() {
        uiState.setValue(new CreateLeaveUiState.Loading());

        disposable.add(Single.zip(
                        getAllLeaveTypeUseCase.execute()
                                .subscribeOn(Schedulers.io())
                                .timeout(5, TimeUnit.SECONDS),

                        getUserProfileUseCase.execute()
                                .subscribeOn(Schedulers.io())
                                .timeout(5, TimeUnit.SECONDS),

                        (leaveTypes, userProfile) -> {
                            this.cachedLeaveTypes = leaveTypes;
                            this.cachedUserProfile = userProfile;
                            return new Pair<>(leaveTypes, userProfile);
                        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        pair -> {
                            if (pair.first == null || pair.first.isEmpty()) {
                                uiState.setValue(new CreateLeaveUiState.Error("Danh sách loại nghỉ phép rỗng", null, null));
                            } else if (pair.second == null) {
                                uiState.setValue(new CreateLeaveUiState.Error("Không thể tải thông tin người dùng", pair.first, null));
                            } else {
                                uiState.setValue(new CreateLeaveUiState.DataLoaded(pair.first, pair.second));
                            }
                        },
                        throwable -> {
                            uiState.setValue(new CreateLeaveUiState.Error(
                                    throwable.getMessage(),
                                    cachedLeaveTypes,
                                    cachedUserProfile
                            ));
                        }
                ));
    }

    public void sendLeaveApplication(LeaveApplicationRequest request) {
        uiState.setValue(new CreateLeaveUiState.Loading());

        disposable.add(createLeaveAppUseCase.execute(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (response != null) {
                                uiState.setValue(new CreateLeaveUiState.LeaveCreated(response));
                            } else {
                                uiState.setValue(new CreateLeaveUiState.Error("Phản hồi không có sẵn", cachedLeaveTypes, cachedUserProfile));
                            }
                        },
                        throwable -> {
                            uiState.setValue(new CreateLeaveUiState.Error(throwable.getMessage(), cachedLeaveTypes, cachedUserProfile));
                        }
                ));
    }

    public void refreshData() {
        loadInitialData();
    }

    public Integer getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Integer leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
