package com.managerapp.personnelmanagerapp.presentation.forgetpass;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentResetPasswordBinding;
import com.managerapp.personnelmanagerapp.presentation.main.MainActivity;
import com.managerapp.personnelmanagerapp.presentation.state.ForgotPasswordState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResetPasswordFragment extends BaseFragment {
    private FragmentResetPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;
    private String email;

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
                navigateToMainActivity(); // Thay đổi tên method cho rõ nghĩa
            } else if (state instanceof ForgotPasswordState.Error) {
                showLoading(false);
                showError(((ForgotPasswordState.Error) state).getErrorMessage());
            }
        });
    }

    private void setupListeners() {
        binding.btnResetPassword.setOnClickListener(v -> {
            String newPassword = binding.edtNewPassword.getText().toString().trim();
            String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();
            if (validatePasswords(newPassword, confirmPassword)) {
                viewModel.resetPassword(newPassword);
            }
        });
    }

    private boolean validatePasswords(String newPassword, String confirmPassword) {
        if (newPassword.isEmpty()) {
            binding.edtNewPassword.setError("Vui lòng nhập mật khẩu mới");
            return false;
        }
        if (newPassword.length() < 6) {
            binding.edtNewPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }
        if (!newPassword.equals(confirmPassword)) {
            binding.edtConfirmPassword.setError("Mật khẩu không khớp");
            return false;
        }
        return true;
    }

    private void navigateToMainActivity() {
        // Thay vì popBackStack, chúng ta sẽ start MainActivity
        Intent intent = new Intent(requireActivity(), MainActivity.class);

        // Clear task và tạo new task để xóa hết back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Kết thúc activity hiện tại nếu cần
        requireActivity().finish();
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