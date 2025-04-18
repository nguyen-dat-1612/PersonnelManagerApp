package com.managerapp.personnelmanagerapp.presentation.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.databinding.ActivityFeedBackBinding;

public class FeedBackActivity extends BaseActivity {

    private ActivityFeedBackBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nút quay về
        binding.backBtn.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("key", "value"); // Thêm dữ liệu nếu cần
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

    }
}