package com.managerapp.personnelmanagerapp.presentation.profile.viewmodel;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;
import com.managerapp.personnelmanagerapp.domain.usecase.user.GetUserUseCase;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.managerapp.personnelmanagerapp.R;

@HiltViewModel
public class UserViewModel extends ViewModel {
    private final GetUserUseCase getUserUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<UiState<UserProfileResponse>> uiState = new MutableLiveData<>();
    private final MutableLiveData<List<DataItem>> userInfoList = new MutableLiveData<>();

    @Inject
    public UserViewModel(GetUserUseCase getUserUseCase) {
        this.getUserUseCase = getUserUseCase;
    }

    public LiveData<UiState<UserProfileResponse>> getUiState() {
        return uiState;
    }

    public LiveData<List<DataItem>> getUserInfoList() {
        return userInfoList;
    }

    public void loadUser(Context context) {
        Log.d("API Hashcode Use Case", String.valueOf(getUserUseCase.hashCode()));
        uiState.setValue(UiState.Loading.getInstance());
        disposables.add(
                getUserUseCase.execute()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .timeout(5, TimeUnit.SECONDS)
                        .subscribe(user -> {
                                    if (user != null) {
                                        uiState.postValue(new UiState.Success<>(user));
                                        userInfoList.postValue(convertToDataItemList(user, context)); // Chuyển đổi dữ liệu tại đây
                                    } else {
                                        uiState.postValue(new UiState.Error("UserEntity not found"));
                                    }
                                },
                                throwable -> uiState.postValue(new UiState.Error(throwable.getMessage()))
                        )
        );
    }

    // Chuyển đổi UserProfileResponse thành danh sách DataItem
    private List<DataItem> convertToDataItemList(UserProfileResponse user, Context context) {
        List<DataItem> items = new ArrayList<>();
        Resources res = context.getResources();

        items.add(new DataItem(res.getString(R.string.user_email), user.getEmail()));
        items.add(new DataItem(res.getString(R.string.user_number_cccd), user.getNumberCCCD()));
        items.add(new DataItem(res.getString(R.string.user_phone_number), user.getPhoneNumber()));

        String dobString;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
                    .parse(user.getDob());
            dobString = android.text.format.DateFormat.getDateFormat(context).format(date);
        } catch (Exception e) {
            dobString = res.getString(R.string.not_available);
        }
        items.add(new DataItem(res.getString(R.string.user_dob), dobString));

        // Xử lý giới tính
        String genderString = res.getString(R.string.gender_other);
        if ("male".equalsIgnoreCase(user.getGender())) {
            genderString = res.getString(R.string.gender_male);
        } else if ("female".equalsIgnoreCase(user.getGender())) {
            genderString = res.getString(R.string.gender_female);
        }
        items.add(new DataItem(res.getString(R.string.user_gender), genderString));

        // Thông tin khác
        items.add(new DataItem(res.getString(R.string.user_address), user.getAddress()));
        items.add(new DataItem(res.getString(R.string.user_ethnicity), user.getEthnicity()));
        items.add(new DataItem(res.getString(R.string.user_religion), user.getReligion()));
        items.add(new DataItem(res.getString(R.string.user_tax_code), user.getTaxCode()));
        items.add(new DataItem(res.getString(R.string.user_degree), user.getDegree()));

        // Trạng thái
        String statusString = res.getString(R.string.not_available);
        if (user.getStatus() != null) {
            switch (user.getStatus()) {
                case "PENDING":
                    statusString = res.getString(R.string.user_status_pending);
                    break;
                case "EXPIRED":
                    statusString = res.getString(R.string.user_status_expired);
                    break;
                case "TERMINATED":
                    statusString = res.getString(R.string.user_status_terminated);
                    break;
            }
        }
        items.add(new DataItem(res.getString(R.string.user_status), statusString));

        return items;
    }
}
