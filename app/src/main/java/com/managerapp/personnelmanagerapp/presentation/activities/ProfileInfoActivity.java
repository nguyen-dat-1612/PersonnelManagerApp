package com.managerapp.personnelmanagerapp.presentation.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityProfileInfoBinding;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;
import com.managerapp.personnelmanagerapp.domain.model.User;
import com.managerapp.personnelmanagerapp.presentation.adapters.ProfileAdapter;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileInfoActivity extends BaseActivity {

    private static final String TAG = "ProfileInfoActivity";
    private ActivityProfileInfoBinding binding;
    private UserViewModel viewModel;
    private ProfileAdapter profileAdapter;
    private List<DataItem> userInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        setupRecyclerView();
        setupListeners();
        loadUserInfo();
    }

    private void setupRecyclerView() {
        profileAdapter = new ProfileAdapter(userInfoList);
        binding.recyclerViewInfomation.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewInfomation.setAdapter(profileAdapter);
        binding.recyclerViewInfomation.setHasFixedSize(true);
    }

    private void setupListeners() {
        binding.swipeRefresh.setOnRefreshListener(this::loadUserInfo);

        binding.backBtn.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("key", "value");
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    private void loadUserInfo() {
        viewModel.getUiState().observe(this, state -> {
            binding.swipeRefresh.setRefreshing(false);

            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.content.setVisibility(INVISIBLE);
            }
            else if (state instanceof UiState.Success) {
                User user = ((UiState.Success<User>) state).getData();
                updateUIWithUserData(user);
                binding.content.setVisibility(VISIBLE);
            }
            else if (state instanceof UiState.Error) {
                String errorMsg = ((UiState.Error) state).getErrorMessage();
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error loading profile: " + errorMsg);
                binding.content.setVisibility(INVISIBLE);
            }
        });
        viewModel.loadUser();
    }

    private void updateUIWithUserData(User user) {
        if (user == null) return;

        userInfoList.clear();
        userInfoList.addAll(convertUserToDataItemList(user, this));
        profileAdapter.notifyDataSetChanged();
        binding.userNameText.setText(user.getFullName());
        binding.magvtext.setText("MSGV: " + user.getId());
        // Hiển thị thông tin avatar nếu có
        if (!TextUtils.isEmpty(user.getAvatar())) {
            Glide.with(this)
                    .load(user.getAvatar()) // Truyền URL vào đây
                    .placeholder(R.drawable.ic_default_avatar) // Ảnh mặc định khi đang tải
                    .error(R.drawable.ic_broken_image) // Ảnh hiển thị khi lỗi
                    .circleCrop() // Cắt thành hình tròn
                    .into(binding.imageUser);

            binding.imageUser.setVisibility(VISIBLE);
        } else {
            // Nếu không có avatar, hiển thị ảnh mặc định
            Glide.with(this)
                    .load(R.drawable.ic_default_avatar)
                    .into(binding.imageUser);
        }
    }

    private List<DataItem> convertUserToDataItemList(User user, Context context) {
        List<DataItem> items = new ArrayList<>();
        Resources res = context.getResources();

        items.add(new DataItem(res.getString(R.string.user_email), user.getEmail()));
        items.add(new DataItem(res.getString(R.string.user_number_cccd), user.getNumberCCCD()));
        items.add(new DataItem(res.getString(R.string.user_phone_number), user.getPhoneNumber()));

        // Xử lý ngày sinh
        String dobString = user.getDob() != null
                ? DateFormat.getDateFormat(context).format(user.getDob())
                : res.getString(R.string.not_available);
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
        items.add(new DataItem(res.getString(R.string.user_address),user.getAddress()));
        items.add(new DataItem(res.getString(R.string.user_ethnicity), user.getEthnicity()));
        items.add(new DataItem(res.getString(R.string.user_religion), user.getReligion()));
        items.add(new DataItem(res.getString(R.string.user_tax_code), user.getTaxCode()));
        items.add(new DataItem(res.getString(R.string.user_degree), user.getDegree()));

        // Trạng thái
        String statusString = res.getString(R.string.not_available);
        if (user.getStatus() != null) {
            switch (user.getStatus()) {
                case PENDING:
                    statusString = res.getString(R.string.user_status_pending);
                    break;
                case EXPIRED:
                    statusString = res.getString(R.string.user_status_expired);
                    break;
                case TERMINATED:
                    statusString = res.getString(R.string.user_status_terminated);
                    break;
            }
        }
        items.add(new DataItem(res.getString(R.string.user_status), statusString));

        return items;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}