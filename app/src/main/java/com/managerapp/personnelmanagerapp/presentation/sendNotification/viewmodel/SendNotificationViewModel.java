package com.managerapp.personnelmanagerapp.presentation.sendNotification.viewmodel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.domain.model.Department;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;
import com.managerapp.personnelmanagerapp.domain.usecase.department.GetAllDepartmentsUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.file.UploadPdfUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.notification.CreateNotificationUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.user.SearchUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.sendNotification.ui.SendNotificationUiState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MultipartBody;

@HiltViewModel
public class SendNotificationViewModel extends ViewModel {
    private final String TAG = "SendNotificationViewModel";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final SearchUserUseCase searchUserUseCase;
    private final GetAllDepartmentsUseCase getAllDepartmentsUseCase;
    private final UploadPdfUseCase uploadPdfUseCase;
    private final MutableLiveData<SendNotificationUiState> uiState = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<UserSummary>> selectedUsers = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<UiState<String>> uploadResult = new MutableLiveData<>();
    private final CreateNotificationUseCase createNotificationUseCase;
    private final MutableLiveData<Boolean> sendNotification = new MutableLiveData<>();

    public LiveData<Boolean> getSendNotification() {
        return sendNotification;
    }

    public LiveData<List<UserSummary>> getSelectedUsers() {
        return selectedUsers;
    }

    private final MutableLiveData<List<Department>> departments = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Department>> getDepartment() {
        return departments;
    }
    private final List<String> selectDepartment = new ArrayList();

    public List<String> getSelectedDepartments() {
        return selectDepartment;
    }

    public void selectedDepartment(Department department) {
        selectDepartment.add(department.getId());
    }


    public void removeSelectedDepartment(Department department) {
        selectDepartment.remove(department.getId());
    }
    public List<Long> getSelectedUserIds() {
        List<Long> idList = new ArrayList<>();
        List<UserSummary> users = selectedUsers.getValue();
        if (users != null) {
            for (UserSummary user : users) {
                idList.add((user.getId()));
            }
        }
        return idList;
    }
    public LiveData<UiState<String>> getUploadResult() {
        return uploadResult;
    }

    @Inject
    public SendNotificationViewModel(SearchUserUseCase searchUserUseCase, GetAllDepartmentsUseCase getAllDepartmentsUseCase, UploadPdfUseCase uploadPdfUseCase, CreateNotificationUseCase createNotificationUseCase) {
        this.searchUserUseCase = searchUserUseCase;
        this.getAllDepartmentsUseCase = getAllDepartmentsUseCase;
        this.uploadPdfUseCase = uploadPdfUseCase;
        this.createNotificationUseCase = createNotificationUseCase;
    }

    public LiveData<SendNotificationUiState> getUiState() {
        return uiState;
    }

    public void search(Observable<String> queryObservable) {
        uiState.setValue(SendNotificationUiState.loading());

        Disposable disposable = queryObservable
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(query -> !query.isEmpty())
                .distinctUntilChanged()
                .switchMap(
                        query -> searchUserUseCase.execute(query)
                                .subscribeOn(Schedulers.io())
                                .onErrorReturn(throwable -> {
                                    uiState.setValue(SendNotificationUiState.error(throwable.getMessage()));
                                    return Collections.emptyList();
                                })

                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( summaryList -> {
                    uiState.setValue(SendNotificationUiState.success(summaryList));
                });
        compositeDisposable.add(disposable);
    }

    public void addSelectedUser(UserSummary user) {
        List<UserSummary> current = selectedUsers.getValue();
        if (current == null) current = new ArrayList<>();

        if (!current.contains(user)) {
            current.add(user);
            selectedUsers.setValue(current);
        }
    }

    public void removeSelectedUser(UserSummary user) {
        List<UserSummary> current = selectedUsers.getValue();
        if (current != null && current.remove(user)) {
            selectedUsers.setValue(current);
        }
    }

    public void clearSelectedUsers() {
        selectedUsers.setValue(new ArrayList<>());
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

    public void sendNotification( String title,
                                  String content,
                                  String recipientText,
                                  List<String> attached,
                                  List<Long> receiverIds,
                                  List<String> positionIds,
                                  List<String> departmentIds) {
        disposables.add(createNotificationUseCase.execute(
                        title, content, recipientText, attached,
                        receiverIds, positionIds, departmentIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        value -> {
                            sendNotification.setValue(true);
                        },
                        throwable -> {
                            Log.d(TAG, "sendNotification: " + throwable.getMessage());
                            sendNotification.setValue(false);
                        },
                        () -> {
                            Log.d("sendNotification", "Không có giá trị trả về từ createNotificationUseCase");
                            sendNotification.setValue(false);
                        }
                ));

    }

    public void getDepartments() {
        disposables.add(getAllDepartmentsUseCase.execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        departments::setValue,
                        throwable -> {
                            Log.d(TAG, "getDepartments: " + throwable.getMessage());
                        }
                ));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
