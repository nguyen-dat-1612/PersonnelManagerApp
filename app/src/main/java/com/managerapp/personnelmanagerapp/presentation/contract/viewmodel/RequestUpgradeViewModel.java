package com.managerapp.personnelmanagerapp.presentation.contract.viewmodel;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.request.SalaryPromotionRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.JobGradeResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.model.JobGrade;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.domain.usecase.jobGrade.GetAllJobGradesUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.jobGrade.GetJobGradeByIdUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion.CreateSalaryPromotionUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.contract.state.RequestUpgradeUiState;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.state.CreateLeaveUiState;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.Triple;

@HiltViewModel
public class RequestUpgradeViewModel extends ViewModel {
    private final Context context;
    private final GetUserUseCase getUserProfileUseCase;
    private final GetAllJobGradesUseCase getAllJobGradesUseCase;
    private final GetJobGradeByIdUseCase getLeaveTypeByIdUseCase;
    private final CreateSalaryPromotionUseCase createSalaryPromotionUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<RequestUpgradeUiState> uiState = new MutableLiveData<>();
    private final MutableLiveData<UiState<SalaryPromotion>> createSalaryProUiState = new MutableLiveData<>();
    private List<JobGrade> cachedJobGrades;
    private JobGrade jobGradeResponse;
    private UserProfileResponse cachedUserProfile;

    public LiveData<RequestUpgradeUiState> getUiState() {
        return uiState;
    }
    public LiveData<UiState<SalaryPromotion>> getCreateSalaryProUiState() {
        return createSalaryProUiState;
    }

    @Inject
    public RequestUpgradeViewModel(
            @ApplicationContext Context context ,
            GetUserUseCase getUserProfileUseCase,
            GetAllJobGradesUseCase getAllJobGradesUseCase,
            GetJobGradeByIdUseCase getLeaveTypeByIdUseCase,
            CreateSalaryPromotionUseCase createSalaryPromotionUseCase) {
        this.context = context;
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.getAllJobGradesUseCase = getAllJobGradesUseCase;
        this.getLeaveTypeByIdUseCase = getLeaveTypeByIdUseCase;
        this.createSalaryPromotionUseCase = createSalaryPromotionUseCase;
        loadInitialData();
    }

    public void loadInitialData() {
        uiState.setValue(new RequestUpgradeUiState.Loading());

        disposable.add(Single.zip(
                        getAllJobGradesUseCase.execute()
                                .subscribeOn(Schedulers.io())
                                .timeout(5, TimeUnit.SECONDS),

                        getUserProfileUseCase.execute()
                                .subscribeOn(Schedulers.io())
                                .timeout(5, TimeUnit.SECONDS),

                        getLeaveTypeByIdUseCase.execute()
                                .subscribeOn(Schedulers.io())
                                .timeout(5, TimeUnit.SECONDS),

                        (jobGrades, userProfile, jobGradeById) -> {
                            return new Triple<List<JobGrade>, UserProfileResponse, JobGrade>(
                                    jobGrades, userProfile, jobGradeById
                            );
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        triple -> {
                            List<JobGrade> jobGrades = triple.getFirst();
                            UserProfileResponse userProfile = triple.getSecond();
                            JobGrade jobGradeById = triple.getThird();

                            this.cachedJobGrades = jobGrades;
                            this.cachedUserProfile = userProfile;
                            this.jobGradeResponse = jobGradeById;

                            if (jobGrades == null || jobGrades.isEmpty()) {
                                uiState.setValue(new RequestUpgradeUiState.Error(context.getString(R.string.error_empty_job_grade_list), null, null, null));
                            } else if (userProfile == null) {
                                uiState.setValue(new RequestUpgradeUiState.Error(context.getString(R.string.error_cannot_load_user_profile), jobGrades, null, jobGradeById));
                            } else if (jobGradeById == null) {
                                uiState.setValue(new RequestUpgradeUiState.Error(context.getString(R.string.error_cannot_load_current_job_grade), jobGrades, userProfile, null));
                            } else {
                                uiState.setValue(new RequestUpgradeUiState.DataLoaded(jobGrades, userProfile, jobGradeById));
                            }
                        },
                        throwable -> {
                            uiState.setValue(new RequestUpgradeUiState.Error(
                                    throwable.getMessage(),
                                    cachedJobGrades,
                                    cachedUserProfile,
                                    jobGradeResponse
                            ));
                        }
                )
        );
    }

    public void createSalaryPromotion(String reason, String currentJobGradeId, String requestJobGradeId, Long userId) {
        createSalaryProUiState.setValue(UiState.Loading.getInstance());

        disposable.add(createSalaryPromotionUseCase.execute(new SalaryPromotionRequest(reason, currentJobGradeId, requestJobGradeId, userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        salaryPromotion -> {
                            createSalaryProUiState.setValue(new UiState.Success<>(salaryPromotion));
                        },
                        throwable -> {
                            createSalaryProUiState.setValue(new UiState.Error(throwable.getMessage()));
                        }
                )
        );
    }

}
