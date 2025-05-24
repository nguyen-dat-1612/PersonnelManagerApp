package com.managerapp.personnelmanagerapp.presentation.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

public abstract class BaseFragment extends Fragment {
    protected BaseViewModel viewModel;
    private Snackbar snackbar;
    private static final String TAG = "BaseFragment";
    protected SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        viewModel = new ViewModelProvider(requireActivity()).get(BaseViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        observeNetworkStatus(view);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideNoInternetSnackbar();
    }

    private void observeNetworkStatus(View view) {
        viewModel.getNetworkStatus().observe(getViewLifecycleOwner(), isConnected -> {
            if (!isConnected) {
                showNoInternetSnackbar(view);
            } else {
                hideNoInternetSnackbar();
            }
        });
    }

    private void showNoInternetSnackbar(View view) {
        if (snackbar == null || !snackbar.isShown()) {
            snackbar = Snackbar.make(view, "Không có kết nối mạng!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Thử lại", v -> reloadFragment());
            snackbar.show();
        }
    }

    private void hideNoInternetSnackbar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private void reloadFragment() {
        if (isAdded()) {
            getParentFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}
