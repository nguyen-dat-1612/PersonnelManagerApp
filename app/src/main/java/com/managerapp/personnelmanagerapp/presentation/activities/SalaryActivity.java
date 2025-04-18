package com.managerapp.personnelmanagerapp.presentation.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivitySalaryBinding;

public class SalaryActivity extends BaseActivity {
    private NavController navController;
    private ActivitySalaryBinding binding;
    private final String TAG = "SalaryActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySalaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.salary_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            throw new IllegalStateException("NavHostFragment is null. Check activity_request.xml.");
        }
    }
    public boolean onSupportNavigateUp() {
        boolean result = navController != null && (navController.navigateUp() || super.onSupportNavigateUp());
        Log.d(TAG, "onSupportNavigateUp: Result = " + result);
        return result;
    }
}