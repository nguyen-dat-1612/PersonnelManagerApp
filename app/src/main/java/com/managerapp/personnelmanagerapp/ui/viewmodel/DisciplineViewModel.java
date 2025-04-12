package com.managerapp.personnelmanagerapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetDisciplineAssignmentsUseCase;
import com.managerapp.personnelmanagerapp.ui.state.DisciplineState;
import com.managerapp.personnelmanagerapp.ui.state.RewardState;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
@HiltViewModel
public class DisciplineViewModel extends ViewModel {
    private final GetDisciplineAssignmentsUseCase getDisciplineAssignmentsUseCase;
    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<DisciplineState> disciplineState = new MutableLiveData<>();

    @Inject
    public DisciplineViewModel(GetDisciplineAssignmentsUseCase getDisciplineAssignmentsUseCase) {
        this.getDisciplineAssignmentsUseCase = getDisciplineAssignmentsUseCase;
    }


    public LiveData<DisciplineState> getDisciplineState() {
        return disciplineState;
    }

    public void loadAllRewards(int userId) {
        disciplineState.postValue(new DisciplineState.Loading());
        disposables.add(
                getDisciplineAssignmentsUseCase.execute(userId)
                        .timeout(10, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( response -> {
                                    if (response.isEmpty()) {
                                        disciplineState.postValue(new DisciplineState.Error("Danh sách khen thưởng trống"));
                                    } else {
                                        disciplineState.postValue(new DisciplineState.ListSuccess(response));
                                    }
                                },
                                throwable -> {
                                    disciplineState.postValue(new DisciplineState.Error("Đã có lỗi xảy ra: " + throwable.getMessage()));
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
