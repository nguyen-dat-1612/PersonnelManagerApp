package com.managerapp.personnelmanagerapp.presentation.forgetpass;

import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgotPasswordActivity extends BaseActivity {

    private NavController navController;
    private final String TAG = "ForgotPasswordActivityLifecycle";
    private ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_forgot_password);
        forgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        setupNavigation();
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.forgot_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            throw new IllegalStateException("NavHostFragment is null. Check activity_forgot_password.xml.");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        boolean result = navController != null && (navController.navigateUp() || super.onSupportNavigateUp());
        Log.d(TAG, "onSupportNavigateUp: Result = " + result);
        return result;
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (!navController.popBackStack()) {
            super.onBackPressed();
        }
    }

    // --- Lifecycle Logging ---

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
