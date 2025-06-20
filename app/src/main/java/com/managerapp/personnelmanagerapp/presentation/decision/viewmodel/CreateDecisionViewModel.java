package com.managerapp.personnelmanagerapp.presentation.decision.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PositionResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.SalaryPromotionResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.SeniorityAllowanceRuleResponse;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;
import com.managerapp.personnelmanagerapp.domain.usecase.decision.CreateDecisionUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.position.GetPositionUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.salarypromotion.GetSalaryPromotionPendingUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.seniority.GetSeniorityAllowanceRuleUseCase;
import com.managerapp.personnelmanagerapp.domain.usecase.user.SearchUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.OptionItem;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.sendNotification.ui.SendNotificationUiState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class CreateDecisionViewModel extends ViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final CreateDecisionUseCase createDecisionUseCase;
    private final SearchUserUseCase searchUserUseCase;

    private final GetPositionUseCase getPositionUseCase;
    private final GetSeniorityAllowanceRuleUseCase getSeniorityAllowanceRuleUseCase;

    private final GetSalaryPromotionPendingUseCase getSalaryPromotionPendingUseCase;
    private final MutableLiveData<SendNotificationUiState> searchUiState = new MutableLiveData<>();
    public LiveData<SendNotificationUiState> getSearchUiState() {return searchUiState; }
    private MutableLiveData<UiState<DecisionResponse>> uiState = new MutableLiveData<>();

    public LiveData<UiState<DecisionResponse>> getUiState() {
        return uiState;
    }
    private final MutableLiveData<List<OptionItem>> optionalOptions = new MutableLiveData<>();
    public LiveData<List<OptionItem>> getOptionalOptions() { return optionalOptions; }

    @Inject
    public CreateDecisionViewModel(CreateDecisionUseCase createDecisionUseCase, SearchUserUseCase searchUserUseCase, GetPositionUseCase getPositionUseCase, GetSeniorityAllowanceRuleUseCase getSeniorityAllowanceRuleUseCase, GetSalaryPromotionPendingUseCase getSalaryPromotionPendingUseCase) {
        this.createDecisionUseCase = createDecisionUseCase;
        this.searchUserUseCase = searchUserUseCase;
        this.getPositionUseCase = getPositionUseCase;
        this.getSeniorityAllowanceRuleUseCase = getSeniorityAllowanceRuleUseCase;
        this.getSalaryPromotionPendingUseCase = getSalaryPromotionPendingUseCase;
    }


    public void createDecision(DecisionRequest decisionRequest) {
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(createDecisionUseCase.execute(decisionRequest)
                .subscribeOn(Schedulers.io())
                .timeout(10, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        decisionResponse -> {
                            uiState.setValue(new UiState.Success(decisionResponse));
                        },
                        throwable -> {
                            uiState.setValue(new UiState.Error(throwable.getMessage()));
                })
        );

    }
    public void search(Observable<String> queryObservable) {
        searchUiState.setValue(SendNotificationUiState.loading());

        Disposable disposable = queryObservable
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(query -> !query.isEmpty())
                .distinctUntilChanged()
                .switchMap(
                        query -> searchUserUseCase.execute(query)
                                .subscribeOn(Schedulers.io())
                                .onErrorReturn(throwable -> {
                                    searchUiState.setValue(SendNotificationUiState.error(throwable.getMessage()));
                                    return Collections.emptyList();
                                })

                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( summaryList -> {
                    searchUiState.setValue(SendNotificationUiState.success(summaryList));
                });
        disposables.add(disposable);
    }

    public void getSeniorityAllowanceOptions() {
        disposables.add(getSeniorityAllowanceRuleUseCase.execute(
                "PENDING")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        options -> {
                            optionalOptions.setValue(mapToOptionItems(options));
                        },
                        throwable -> {
                            // Xử lý lỗi
                            Log.e("CreateDecisionViewModel", "Error fetching promotion options: " + throwable.getMessage());
                        }
                )
        );
    }


    public void getPositionOptions() {
        disposables.add(getPositionUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        options -> {
                            optionalOptions.setValue(mapToOptionItems(options));
                        },
                        throwable -> {
                            // Xử lý lỗi
                            Log.e("CreateDecisionViewModel", "Error fetching promotion options: " + throwable.getMessage());
                        }
                )
        );
    }


    public final Map<DecisionEnum, Runnable> decisionHandlers = new HashMap<DecisionEnum, Runnable>() {{
        put(DecisionEnum.PROMOTION, () -> getPositionOptions());
        put(DecisionEnum.SENIORITY_ALLOWANCE, () -> getSeniorityAllowanceOptions());
    }};

    private List<OptionItem> mapToOptionItems(List<? extends Object> rawList) {
        List<OptionItem> result = new ArrayList<>();

        for (Object item : rawList) {
            if (item instanceof SalaryPromotionResponse) {
                SalaryPromotionResponse obj = (SalaryPromotionResponse) item;
                result.add(new OptionItem(obj.getId(), obj.getUserName()));

            } else if (item instanceof SeniorityAllowanceRuleResponse) {
                SeniorityAllowanceRuleResponse obj = (SeniorityAllowanceRuleResponse) item;
                result.add(new OptionItem(obj.getId(), obj.getDescription()));

            } else if (item instanceof PositionResponse) {
                PositionResponse obj = (PositionResponse) item;
                result.add(new OptionItem(obj.getId(), obj.getName()));
            }
        }
        return result;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
