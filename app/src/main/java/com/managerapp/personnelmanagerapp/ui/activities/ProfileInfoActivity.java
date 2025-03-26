package com.managerapp.personnelmanagerapp.ui.activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityProfileInfoBinding;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;
import com.managerapp.personnelmanagerapp.domain.model.User;
import com.managerapp.personnelmanagerapp.ui.adapters.ProfileAdapter;
import com.managerapp.personnelmanagerapp.ui.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfoActivity extends AppCompatActivity {

    private static final String TAG = "ProfileInfoActivity";
    private ActivityProfileInfoBinding binding;
    private ProfileAdapter adapter;
    private UserViewModel viewModel;
    private List<DataItem> userInfoList = new ArrayList<>();
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProfileInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.loadUser(1, user -> {
            this.user = user;

            List<DataItem> userInfoList = new ArrayList<>();
            userInfoList.add(new DataItem(getString(R.string.full_name), user.getFullName()));
            userInfoList.add(new DataItem(getString(R.string.gender), user.getGender().getVietnameseName()));
            userInfoList.add(new DataItem(getString(R.string.date_of_birth), user.getDateOfBirth().toString()));
            userInfoList.add(new DataItem(getString(R.string.email), user.getEmail()));
            userInfoList.add(new DataItem(getString(R.string.phone_number), user.getPhoneNumber()));
            userInfoList.add(new DataItem(getString(R.string.cccd), user.getCccd()));
            userInfoList.add(new DataItem(getString(R.string.permanent_address), user.getPermanentAddress()));
            userInfoList.add(new DataItem(getString(R.string.ethnicity), user.getEthnicity() != null ? user.getEthnicity().toString() : "N/A"));
            userInfoList.add(new DataItem(getString(R.string.religion), user.getReligion()));
            userInfoList.add(new DataItem(getString(R.string.status), user.getStatus()));
            userInfoList.add(new DataItem(getString(R.string.academic_degree), user.getAcademicDegree()));

            runOnUiThread(() -> {
                adapter = new ProfileAdapter(userInfoList);
                binding.recyclerViewInfomation.setAdapter(adapter);
            });

        }, throwable -> {
            Toast.makeText(this, "Lỗi tải thông tin người dùng!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Lỗi khi tải thông tin người dùng", throwable);
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("key", "value"); // Thêm dữ liệu nếu cần
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}