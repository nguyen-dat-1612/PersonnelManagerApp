package com.managerapp.personnelmanagerapp.ui.activities;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;

import com.managerapp.personnelmanagerapp.ui.base.BaseActivity;
import com.managerapp.personnelmanagerapp.databinding.ActivitySplashBinding;

public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}