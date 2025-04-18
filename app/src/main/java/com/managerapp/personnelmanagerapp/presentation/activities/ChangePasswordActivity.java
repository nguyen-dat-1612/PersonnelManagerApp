package com.managerapp.personnelmanagerapp.presentation.activities;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityChangePasswordBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.ChangePasswordViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChangePasswordActivity extends BaseActivity {

    private ActivityChangePasswordBinding binding;
    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private ChangePasswordViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ChangePasswordViewModel.class);

        setupListeners();
        setupObservers();

    }

    private void setupObservers() {
        viewModel.getChangePasswordState().observe(this, state -> {
            if (state instanceof UiState.Loading) {
                showLoading(true);
            } else if (state instanceof UiState.Success) {
                showLoading(false);
                String msg = ((UiState.Success<String>) state).getData();
                showToast(msg);
                finish();
            } else if (state instanceof UiState.Error) {
                showLoading(false);
                String err = ((UiState.Error<?>) state).getErrorMessage();
                showToast(err);
            }
        });
    }


    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void setupListeners() {
        // Xử lý sự kiện click cho nút hiển thị mật khẩu mới
        binding.newPasswordToggle.setOnClickListener(v -> {
            isNewPasswordVisible = !isNewPasswordVisible;
            if (isNewPasswordVisible) {
                binding.edtNewPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.newPasswordToggle.setImageResource(R.drawable.ic_eye_on);
            } else {
                binding.edtNewPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.newPasswordToggle.setImageResource(R.drawable.ic_eye_off);
            }
            binding.edtNewPass.setSelection(binding.edtNewPass.getText().length()); // Giữ con trỏ văn bản ở cuối
        });

        // Xử lý sự kiện click cho nút hiển thị xác nhận mật khẩu
        binding.confirmPasswordToggle.setOnClickListener(v -> {
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            if (isConfirmPasswordVisible) {
                binding.edtOldPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.confirmPasswordToggle.setImageResource(R.drawable.ic_eye_on);
            } else {
                binding.edtOldPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.confirmPasswordToggle.setImageResource(R.drawable.ic_eye_off);
            }
            binding.edtOldPass.setSelection(binding.edtOldPass.getText().length()); // Giữ con trỏ văn bản ở cuối
        });

        binding.saveButton.setOnClickListener(v -> {
           viewModel.loadChangePassword(binding.edtOldPass.getText().toString(), binding.edtNewPass.getText().toString());
        });

        // Xử lý sự kiện click cho nút quay lại
        binding.backBtn.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void showLoading(boolean isLoading) {
        binding.progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}