package com.managerapp.personnelmanagerapp.presentation.leaveapp;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.CreateLeaveAppUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.leaveapp.GetAllLeaveTypeUseCase;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.utils.LocalDataManager;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class CreateLeaveViewModel extends ViewModel {

    private final String TAG = "CreateLeaveViewModel";

    private final GetAllLeaveTypeUseCase getAllLeaveTypeUseCase;
    private final CreateLeaveAppUseCase createLeaveAppUseCase;
    private final LocalDataManager localDataManager;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<UiState<List<LeaveType>>> leaveTypesState = new MutableLiveData<>();
    private final MutableLiveData<UiState<LeaveApplicationResponse>> createLeaveState = new MutableLiveData<>();

    private long userId = -1;
    @Inject
    public CreateLeaveViewModel(GetAllLeaveTypeUseCase getAllLeaveTypeUseCase, CreateLeaveAppUseCase createLeaveAppUseCase, LocalDataManager localDataManager) {
        this.getAllLeaveTypeUseCase = getAllLeaveTypeUseCase;
        this.createLeaveAppUseCase = createLeaveAppUseCase;
        this.localDataManager = localDataManager;

        try {
            userId = Long.parseLong(localDataManager.getUserId());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Không thể chuyển đổi userId thành số: " + e.getMessage());
        }
    }

    public LiveData<UiState<LeaveApplicationResponse>> getCreateLeaveState() {
        return createLeaveState;
    }

    public LiveData<UiState<List<LeaveType>>> getLeaveTypesState() {
        return leaveTypesState;
    }

    public void getLeaveTypes() {
        leaveTypesState.setValue(UiState.Loading.getInstance());
        disposable.add(getAllLeaveTypeUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( leaveTypes ->
                        {
                            if (!leaveTypes.isEmpty()) {
                                leaveTypesState.postValue(new UiState.Success<>(leaveTypes));
                            } else {
                                leaveTypesState.postValue(new UiState.Error<>("Danh sách loại nghỉ phép rỗng"));
                            }
                        },
                        throwable -> {
                            leaveTypesState.postValue(new UiState.Error<>(throwable.getMessage()));
                        }

                ));
    }

    public void sendLeaveApplication(LeaveApplicationRequest leaveApplicationRequest) {
        if (userId == -1 || userId == 0) {
            createLeaveState.postValue(new UiState.Error<>("User ID is null"));
            return;
        }

        leaveApplicationRequest.setUserId(userId);
        createLeaveState.setValue(UiState.Loading.getInstance());

        disposable.add(createLeaveAppUseCase.execute(leaveApplicationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        leaveApplicationResponse -> {
                            if (leaveApplicationResponse != null) {
                                createLeaveState.postValue(new UiState.Success<>(leaveApplicationResponse));
                            } else {
                                createLeaveState.postValue(new UiState.Error<>("Phản hồi không có sẵn"));
                            }
                        },
                        throwable -> {
                            Log.e(TAG,throwable.getMessage());
                            createLeaveState.postValue(new UiState.Error<>(throwable.getMessage()));
                        }
                ));

    }
}
