package com.managerapp.personnelmanagerapp.ui.activities;

import android.os.Bundle;
import android.text.InputType;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;


    private boolean isNewPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Xử lý sự kiện click cho nút hiển thị mật khẩu mới
        binding.newPasswordToggle.setOnClickListener(v -> {
            isNewPasswordVisible = !isNewPasswordVisible;
            if (isNewPasswordVisible) {
                binding.newPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.newPasswordToggle.setImageResource(R.drawable.ic_eye_on);
            } else {
                binding.newPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.newPasswordToggle.setImageResource(R.drawable.ic_eye_off);
            }
            binding.newPasswordEditText.setSelection(binding.newPasswordEditText.getText().length()); // Giữ con trỏ văn bản ở cuối
        });

        // Xử lý sự kiện click cho nút hiển thị xác nhận mật khẩu
        binding.confirmPasswordToggle.setOnClickListener(v -> {
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            if (isConfirmPasswordVisible) {
                binding.confirmPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.confirmPasswordToggle.setImageResource(R.drawable.ic_eye_on);
            } else {
                binding.confirmPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.confirmPasswordToggle.setImageResource(R.drawable.ic_eye_off);
            }
            binding.confirmPasswordEditText.setSelection(binding.confirmPasswordEditText.getText().length()); // Giữ con trỏ văn bản ở cuối
        });

        binding.backBtn.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

    }
}