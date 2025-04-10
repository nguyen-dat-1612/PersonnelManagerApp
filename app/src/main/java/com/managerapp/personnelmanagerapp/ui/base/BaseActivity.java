package com.managerapp.personnelmanagerapp.ui.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.managerapp.personnelmanagerapp.ui.viewmodel.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public abstract class BaseActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        observeNetworkStatus();
    }

    private void observeNetworkStatus() {
        viewModel.getNetworkStatus().observe(this, isConnected -> {
            if (!isConnected) {
                showNoInternetSnackbar();
            } else {
                hideNoInternetSnackbar();
            }
        });
    }

    private void showNoInternetSnackbar() {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "Không có kết nối mạng!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Thử lại", v -> recreate()); // Reload Activity khi nhấn "Thử lại"
            snackbar.show();
        }
    }

    private void hideNoInternetSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }
}

