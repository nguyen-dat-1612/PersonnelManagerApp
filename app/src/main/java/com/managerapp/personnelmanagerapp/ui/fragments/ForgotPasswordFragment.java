package com.managerapp.personnelmanagerapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentForgotPasswordBinding;
import com.managerapp.personnelmanagerapp.ui.activities.LoginActivity;
import com.managerapp.personnelmanagerapp.ui.state.ForgotPasswordState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.ForgotPasswordViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgotPasswordFragment extends BaseFragment {
    private FragmentForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);

        setupObservers();
        setupListeners();
        setupBackNavigation();
    }

    private void setupObservers() {
        viewModel.getForgotPasswordState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ForgotPasswordState.Loading) {
                showLoading(true);
            } else if (state instanceof ForgotPasswordState.EmailSent) {
                showLoading(false);
                navigateToVerifyOtp(((ForgotPasswordState.EmailSent) state).getEmail());
            } else if (state instanceof ForgotPasswordState.Error) {
                showLoading(false);
                showError(((ForgotPasswordState.Error) state).getErrorMessage());
            }
        });
    }

    private void setupListeners() {
        binding.btnSendEmail.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString().trim();
            if (validateEmail(email)) {
                viewModel.sendForgotPassword(email);
            }
        });

        binding.btnBackLogin.setOnClickListener(v -> navigateBackToLogin());
    }

    private void setupBackNavigation() {
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        navigateBackToLogin();
                    }
                });
    }

    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            binding.editTextEmail.setError("Vui lòng nhập email");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError("Email không hợp lệ");
            return false;
        }
        return true;
    }

    private void navigateToVerifyOtp(String email) {
        NavDirections action = ForgotPasswordFragmentDirections
                .actionForgotPasswordFragmentToVerifyOtpFragment(email);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    private void navigateBackToLogin() {
        // Sử dụng Navigation Component để quay lại nếu có back stack
        if (!Navigation.findNavController(binding.getRoot()).popBackStack()) {
            // Nếu không có back stack, mở LoginActivity
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    private void showLoading(boolean isLoading) {
        binding.progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.btnSendEmail.setEnabled(!isLoading);
    }

    private void showError(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}