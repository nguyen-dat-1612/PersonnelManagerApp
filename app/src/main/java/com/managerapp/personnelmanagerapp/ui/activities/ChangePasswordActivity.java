package com.managerapp.personnelmanagerapp.ui.activities;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityChangePasswordBinding;
import com.managerapp.personnelmanagerapp.ui.state.ChangePasswordState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.ChangePasswordViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChangePasswordActivity extends AppCompatActivity {

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
            if (state instanceof ChangePasswordState.Loading) {
                showLoading(true);
            } else if (state instanceof  ChangePasswordState.Success) {
                showLoading(false);
                showToast("Đổi mật khẩu thành công");
                finish();
            } else if (state instanceof  ChangePasswordState.Error) {
                showLoading(false);
                showToast("Đổi mật khẩu thất bại");
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

        // Xử lý sự kiện click cho nút quay lại
        binding.backBtn.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    private void showLoading(boolean isLoading) {
        binding.progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}