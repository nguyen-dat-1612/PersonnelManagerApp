package com.managerapp.personnelmanagerapp.presentation.auth.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentResetPasswordBinding;
import com.managerapp.personnelmanagerapp.presentation.auth.viewmodel.ForgotPasswordViewModel;
import com.managerapp.personnelmanagerapp.presentation.auth.state.ForgotPasswordState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResetPasswordFragment extends BaseFragment {

    private final String TAG = "ResetPasswordFragment";
    private FragmentResetPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;
    private String email;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);
        email = ResetPasswordFragmentArgs.fromBundle(getArguments()).getEmail();
        setupObservers();
        setupListeners();
    }
    private void setupObservers() {
        viewModel.getForgotPasswordState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ForgotPasswordState.Loading) {
                showLoading(true);
            } else if (state instanceof ForgotPasswordState.PasswordResetSuccess) {
                showLoading(false);
                navigateToMainActivity();
            } else if (state instanceof ForgotPasswordState.Error) {
                showLoading(false);
                Log.e(TAG, ((ForgotPasswordState.Error) state).getErrorMessage());
                showError(((ForgotPasswordState.Error) state).getErrorMessage());
            }
        });
    }

    private void setupListeners() {
        binding.btnResetPassword.setOnClickListener(v -> {
            String newPassword = binding.edtNewPassword.getText().toString().trim();
            String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();
            if (validatePasswords(newPassword, confirmPassword)) {
                if (this.email == null) {
                    showError(getString(R.string.error_verify_otp));
                    return;
                }
                viewModel.resetPassword(newPassword);
            }
        });

        setupPasswordToggle(binding.edtNewPassword, binding.eyeNewPassword);
        setupPasswordToggle(binding.edtConfirmPassword, binding.eyeConfirmPassword);
    }
    private void setupPasswordToggle(EditText editText, ImageView toggle) {
        toggle.setOnClickListener(v -> {
            boolean isHidden = editText.getTransformationMethod() instanceof PasswordTransformationMethod;
            if (isHidden) {
                editText.setTransformationMethod(null);
                toggle.setImageResource(R.drawable.ic_eye_on); // icon hiển mật khẩu
            } else {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                toggle.setImageResource(R.drawable.ic_eye_off); // icon ẩn mật khẩu
            }
            editText.setSelection(editText.getText().length()); // đặt con trỏ về cuối
        });
    }

    private boolean validatePasswords(String newPassword, String confirmPassword) {
        if (newPassword.isEmpty()) {
            binding.edtNewPassword.setError(getString(R.string.error_new_password_empty));
            return false;
        }
        if (newPassword.length() < 6) {
            binding.edtNewPassword.setError(getString(R.string.error_new_password_length));
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            binding.edtConfirmPassword.setError(getString(R.string.error_password_mismatch));
            return false;
        }
        return true;
    }

    private void navigateToMainActivity() {
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.nav_main, true)
                .build();

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_resetPasswordFragment_to_mainFragment, null, navOptions);

    }

    private void showLoading(boolean isLoading) {
        binding.progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.btnResetPassword.setEnabled(!isLoading);
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