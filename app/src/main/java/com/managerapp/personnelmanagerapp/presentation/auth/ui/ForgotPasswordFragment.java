package com.managerapp.personnelmanagerapp.presentation.auth.ui;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentForgotPasswordBinding;
import com.managerapp.personnelmanagerapp.presentation.auth.viewmodel.ForgotPasswordViewModel;
import com.managerapp.personnelmanagerapp.presentation.auth.state.ForgotPasswordState;
import dagger.hilt.android.AndroidEntryPoint;
import com.managerapp.personnelmanagerapp.R;

@AndroidEntryPoint
public class ForgotPasswordFragment extends BaseFragment {
    private FragmentForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);
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
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        navController = Navigation.findNavController(view);
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

        binding.btnBackLogin.setOnClickListener(v ->{
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
        });
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
            binding.editTextEmail.setError(getString(R.string.error_email_empty));
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextEmail.setError(getString(R.string.error_email_invalid));
            return false;
        }
        return true;
    }

    private void navigateToVerifyOtp(String email) {
        if (isAdded() && navController != null) {
            NavDirections action = ForgotPasswordFragmentDirections
                    .actionForgotPasswordFragmentToVerifyOtpFragment(email);
            try {
                navController.navigate(action);
            } catch (IllegalArgumentException e) {
                Log.e("ForgotPasswordFragment", "Navigation failed: " + e.getMessage());
            }
        }
    }

    private void navigateBackToLogin() {

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