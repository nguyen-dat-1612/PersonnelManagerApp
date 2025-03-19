package com.managerapp.personnelmanagerapp.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityFeedBackBinding;

public class FeedBackActivity extends AppCompatActivity {

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