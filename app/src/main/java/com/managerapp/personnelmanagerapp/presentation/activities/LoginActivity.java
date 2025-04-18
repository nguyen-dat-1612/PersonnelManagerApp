package com.managerapp.personnelmanagerapp.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityLoginBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint  // Nếu bạn dùng Hilt để Inject ViewModel
public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        // Khởi tạo ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Ẩn progress ban đầu
        binding.progressOverlay.setVisibility(View.INVISIBLE);

        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                return;
            }

            binding.progressOverlay.setVisibility(View.VISIBLE); // Hiển thị tiến trình

            // Gọi API Login
            loginViewModel.login(email, password);
        });

        binding.passwordToggle.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                binding.editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                binding.passwordToggle.setImageResource(R.drawable.ic_eye_on);
            } else {
                binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                binding.passwordToggle.setImageResource(R.drawable.ic_eye_off);
            }
            binding.editTextPassword.setSelection(binding.editTextPassword.getText().length()); // Giữ con trỏ văn bản ở cuối
        });

        binding.btnForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Gọi hàm theo dõi trạng thái đăng nhập
        loadLogin();
    }

    private void loadLogin() {
        loginViewModel.getUiState().observe(this, state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.setVisibility(View.VISIBLE); // Hiển thị tiến trình
            } else if (state instanceof UiState.Success) {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.setVisibility(View.INVISIBLE);
                String errorMessage = ((UiState.Error) state).getErrorMessage();
                Toast.makeText(this, "Đăng nhập thất bại: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
