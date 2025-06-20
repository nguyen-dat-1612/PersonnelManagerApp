package com.managerapp.personnelmanagerapp.presentation.changepass.ui;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentChangePasswordBinding;
import com.managerapp.personnelmanagerapp.presentation.changepass.viewmodel.ChangePasswordViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChangePasswordFragment extends Fragment {
    private FragmentChangePasswordBinding binding;
    private boolean isOldPasswordVisible = false;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private ChangePasswordViewModel viewModel;

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupListeners();
        setupObservers();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    private void setupObservers() {
        viewModel.getChangePasswordState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                showLoading(true);
            } else if (state instanceof UiState.Success) {
                showLoading(false);
                showToast(getString(R.string.reset_password_success));
                navController.popBackStack();
            } else if (state instanceof UiState.Error) {
                showLoading(false);
                String err = ((UiState.Error<?>) state).getErrorMessage();
                showToast(err);
            }
        });
    }

    private void showToast(String text) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show();
    }

    private void setupListeners() {
        binding.edtOldPassToggle.setOnClickListener(v -> {
            isOldPasswordVisible = !isOldPasswordVisible;
            setupPasswordToggle(binding.edtOldPass, binding.edtOldPassToggle, isOldPasswordVisible);
        });

        binding.newPasswordToggle.setOnClickListener(v -> {
            isNewPasswordVisible = !isNewPasswordVisible;
            setupPasswordToggle(binding.edtNewPass, binding.newPasswordToggle, isNewPasswordVisible);
        });

        binding.confirmPasswordToggle.setOnClickListener(v -> {
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            setupPasswordToggle(binding.edtConfirmPass, binding.confirmPasswordToggle, isConfirmPasswordVisible);
        });

        binding.saveButton.setOnClickListener(v -> {
            String oldPassword = binding.edtOldPass.getText().toString();
            String newPassword = binding.edtNewPass.getText().toString();
            String confirmPassword = binding.edtConfirmPass.getText().toString();

            if (!validateInputs(oldPassword, newPassword, confirmPassword)) {
                return;
            }

            viewModel.loadChangePassword(oldPassword, newPassword);
        });

        binding.backBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_changePasswordFragment_to_mainFragment);
            navController.popBackStack();
        });
    }

    private void setupPasswordToggle(EditText editText, ImageView toggle, boolean isVisible) {
        if (isVisible) {
            editText.setTransformationMethod(null);
            toggle.setImageResource(R.drawable.ic_eye_on);
        } else {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggle.setImageResource(R.drawable.ic_eye_off);
        }
        editText.setSelection(editText.getText().length());
    }

    private void showLoading(boolean isLoading) {
        binding.progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private boolean validateInputs(String oldPassword, String newPassword, String confirmPassword) {

        if (oldPassword.isEmpty()) {
            binding.edtOldPass.setError(getString(R.string.error_old_password_empty));
            return false;
        }

        if (newPassword.isEmpty()) {
            binding.edtNewPass.setError(getString(R.string.error_new_password_empty));
            return false;
        }

        if (confirmPassword.isEmpty()) {
            binding.edtConfirmPass.setError(getString(R.string.error_confirm_password_empty));
            return false;
        }

        if (!newPassword.equals(confirmPassword)) {
            binding.edtConfirmPass.setError(getString(R.string.error_password_not_match));
            return false;
        }

        if (oldPassword.equals(newPassword)) {
            binding.edtNewPass.setError(getString(R.string.error_new_password_same_as_old));
            return false;
        }

        if (!isPasswordStrong(newPassword)) {
            binding.edtNewPass.setError(getString(R.string.error_password_weak));
            return false;
        }

        return true;
    }

    private boolean isPasswordStrong(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return false;
        }
        return true;
    }
}
