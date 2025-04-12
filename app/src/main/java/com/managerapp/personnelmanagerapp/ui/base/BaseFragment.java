package com.managerapp.personnelmanagerapp.ui.base;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.managerapp.personnelmanagerapp.ui.viewmodel.BaseViewModel;

public abstract class BaseFragment extends Fragment {
    protected BaseViewModel viewModel;
    private Snackbar snackbar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BaseViewModel.class);
        observeNetworkStatus(view);
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
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }
}

