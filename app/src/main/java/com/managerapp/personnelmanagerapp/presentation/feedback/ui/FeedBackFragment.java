package com.managerapp.personnelmanagerapp.presentation.feedback.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentFeedBackBinding;
import com.managerapp.personnelmanagerapp.presentation.feedback.viewmodel.FeedbackViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class FeedBackFragment extends Fragment {

    private FragmentFeedBackBinding binding;
    private FeedbackViewModel feedbackViewModel;
    private  NavController navController ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedbackViewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBackBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        setOnListener();
        onFeedbackSent();
    }

    public void setOnListener() {
        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_feedBackFragment_to_mainFragment);
            navController.popBackStack();
        });

        binding.btnSend.setOnClickListener(v -> {
            String title = binding.edtTitle.getText().toString().trim();
            String reason = binding.edtReason.getText().toString().trim();

            if (validateFeedbackInput(title, reason)) {
                feedbackViewModel.sendFeedback(title, reason);
            }
        });
    }

    private boolean validateFeedbackInput(String title, String reason) {
        // Reset previous errors
        binding.edtTitle.setError(null);
        binding.edtReason.setError(null);

        if (title.isEmpty()) {
            binding.edtTitle.setError(getString(R.string.error_feedback_title_empty));
            binding.edtTitle.requestFocus();
            return false;
        }

        if (title.length() > 100) {
            binding.edtTitle.setError(getString(R.string.error_feedback_title_too_long));
            binding.edtTitle.requestFocus();
            return false;
        }

        if (reason.isEmpty()) {
            binding.edtReason.setError(getString(R.string.error_feedback_reason_empty));
            binding.edtReason.requestFocus();
            return false;
        }

        if (reason.length() < 20) {
            binding.edtReason.setError(getString(R.string.error_feedback_reason_too_short));
            binding.edtReason.requestFocus();
            return false;
        }

        if (reason.length() > 1000) {
            binding.edtReason.setError(getString(R.string.error_feedback_reason_too_long));
            binding.edtReason.requestFocus();
            return false;
        }

        return true;
    }

    public void onFeedbackSent() {
        feedbackViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                showLoading(true);
                hideSuccessView();
            } else if (state instanceof UiState.Success) {
                showLoading(false);
                String message = ((UiState.Success<String>) state).getData();
                showSuccessView();
                navigateBackAfterDelay();
            } else if (state instanceof UiState.Error) {
                showLoading(false);
                hideSuccessView();
                String errorMessage = ((UiState.Error) state).getErrorMessage();
                showErrorToast(errorMessage);
            }
        });
    }

    private void showLoading(boolean show) {
        binding.progressOverlay.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void showSuccessView() {
        binding.viewSendSuccess.getRoot().setVisibility(View.VISIBLE);
    }

    private void hideSuccessView() {
        binding.viewSendSuccess.getRoot().setVisibility(View.GONE);
    }

    private void showErrorToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }

    private void navigateBackAfterDelay() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            navController.navigate(R.id.action_feedBackFragment_to_mainFragment);
            navController.popBackStack();
        }, 3000);
    }
}