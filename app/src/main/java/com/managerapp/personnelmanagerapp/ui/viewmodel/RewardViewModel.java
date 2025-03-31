package com.managerapp.personnelmanagerapp.ui.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.domain.usecase.GetRewardAssignmentByIdUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.GetRewardAssignmentsUseCase;
import com.managerapp.personnelmanagerapp.ui.state.RewardState;

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
    private MutableLiveData<RewardState> rewardState = new MutableLiveData<>();

    @Inject
    public RewardViewModel(GetRewardAssignmentsUseCase getRewardAssignmentsUseCase, GetRewardAssignmentByIdUseCase getRewardAssignmentByIdUseCase) {
        this.getRewardAssignmentsUseCase = getRewardAssignmentsUseCase;
        this.getRewardAssignmentByIdUseCase = getRewardAssignmentByIdUseCase;
    }

    public LiveData<RewardState> getRewardState() {
        return rewardState;
    }

    public void loadAllRewards(int userId) {
        rewardState.postValue(new RewardState.Loading());

        disposables.add(
                getRewardAssignmentsUseCase.execute(userId)
                        .timeout(10, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( response -> {
                                    if (response.isEmpty()) {
                                        rewardState.postValue(new RewardState.Error("Danh sách khen thưởng trống"));
                                    } else {
                                        rewardState.postValue(new RewardState.ListSuccess(response));
                                    }
                                },
                                throwable -> {
                                    rewardState.postValue(new RewardState.Error("Đã có lỗi xảy ra: " + throwable.getMessage()));
                                }
                        )

        );

    }

    public void loadRewardAssignment(int userId, int rewardId) {
        rewardState.postValue(new RewardState.Loading());

        disposables.add(
          getRewardAssignmentByIdUseCase.execute(userId, rewardId)
                  .timeout(10, TimeUnit.SECONDS)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe( rewardAssignment -> {
                      if (rewardAssignment == null) {
                          rewardState.postValue(new RewardState.Error("Không tồn tại khen thưởng này"));
                          Log.e(TAG, "Không tồn tại khen thưởng");
                      } else {
                          rewardState.postValue(new RewardState.DetailSuccess(rewardAssignment));
                          Log.d(TAG, "Lấy khen thưởng thành công");
                      }
                  },
                          throwable -> {
                            rewardState.postValue(new RewardState.Error("Đã có lỗi xảy ra: " + throwable.getMessage()));
                            Log.e(TAG, "Đã có lỗi xảy ra: " + throwable.getMessage());
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
