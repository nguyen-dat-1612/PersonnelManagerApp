package com.managerapp.personnelmanagerapp.ui.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.domain.usecase.CreateLeaveAppUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.GetAllLeaveTypeUseCase;
import com.managerapp.personnelmanagerapp.ui.state.CreateLeaveState;
import com.managerapp.personnelmanagerapp.ui.state.LeaveTypesState;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class CreateLeaveViewModel extends ViewModel {
    private final GetAllLeaveTypeUseCase getAllLeaveTypeUseCase;
    private final CreateLeaveAppUseCase createLeaveAppUseCase;
    private MutableLiveData<LeaveTypesState> leaveTypesState = new MutableLiveData<>();
    private MutableLiveData<CreateLeaveState> createLeaveState = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final String TAG = "CreateLeaveViewModel";

    @Inject
    public CreateLeaveViewModel(GetAllLeaveTypeUseCase getAllLeaveTypeUseCase, CreateLeaveAppUseCase createLeaveAppUseCase) {
        this.getAllLeaveTypeUseCase = getAllLeaveTypeUseCase;
        this.createLeaveAppUseCase = createLeaveAppUseCase;
    }

    public LiveData<LeaveTypesState> getLeaveTypesState() {
        return leaveTypesState;
    }

    public LiveData<CreateLeaveState> getCreateLeaveState() {
        return createLeaveState;
    }

    public void getLeaveTypes() {
        leaveTypesState.setValue(LeaveTypesState.Loading.getInstance());
        disposable.add(getAllLeaveTypeUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( leaveTypes ->
                        {
                            if (!leaveTypes.isEmpty()) {
                                leaveTypesState.postValue(new LeaveTypesState.Success(leaveTypes));
                            } else {
                                leaveTypesState.postValue(new LeaveTypesState.Error("Danh sách loại nghỉ phép rỗng"));
                            }
                        },
                        throwable -> {
                            leaveTypesState.postValue(new LeaveTypesState.Error(throwable.getMessage()));
                        }

                ));
    }

    public void sendLeaveApplication(LeaveApplicationRequest leaveApplicationRequest) {
        createLeaveState.setValue(CreateLeaveState.Loading.getInstance());
        disposable.add(createLeaveAppUseCase.execute(leaveApplicationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        leaveApplicationResponse -> {
                            if (leaveApplicationResponse != null) {
                                createLeaveState.postValue(new CreateLeaveState.Success(leaveApplicationResponse));
                            } else {
                                createLeaveState.postValue(new CreateLeaveState.Error("Phản hồi không có sẵn"));
                            }
                        },
                        throwable -> {
                            Log.e(TAG,throwable.getMessage());
                            createLeaveState.postValue(new CreateLeaveState.Error(throwable.getMessage()));
                        }
                ));

    }
}
