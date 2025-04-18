package com.managerapp.personnelmanagerapp.presentation.activities;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityContractBinding;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractActivity extends BaseActivity {

    private final String TAG = "ContractActivity";
    private ActivityContractBinding binding;
    private NavController navController;
    private long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityContractBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contract_fragment);

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
