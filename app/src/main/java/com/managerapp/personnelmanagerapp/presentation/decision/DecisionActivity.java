package com.managerapp.personnelmanagerapp.presentation.decision;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityRewardDisciplineBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DecisionActivity extends BaseActivity {

    private ActivityRewardDisciplineBinding binding;
    private NavController navController;
    private final String TAG = "RewardDisciplineActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRewardDisciplineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo NavController
        navController = Navigation.findNavController(this, R.id.fragmentContainer);

    }

}