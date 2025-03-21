package com.managerapp.personnelmanagerapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityLoginBinding;
import com.managerapp.personnelmanagerapp.ui.viewmodel.LoginViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint  // Nếu bạn dùng Hilt để Inject ViewModel
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        binding.progressOverlay.setVisibility(View.INVISIBLE);
        // Khởi tạo ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Observe LiveData ngay từ đầu, không đặt trong onClick
        loginViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Log.d("DangNhap", "Đăng nhập thành công!");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginViewModel.getErrorLiveData().observe(this, error -> {
            Log.d("DangNhap", "Đăng nhập thất bại: " + error);
            binding.progressOverlay.setVisibility(View.INVISIBLE);
            try {
                JSONObject jsonObject = new JSONObject(error);
                String errorMessage = jsonObject.optString("message", "Đăng nhập thất bại, vui lòng thử lại!");
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Log.e("DangNhap", "Lỗi parse JSON: " + e.getMessage());
                Toast.makeText(LoginActivity.this, "Lỗi hệ thống, vui lòng thử lại sau!", Toast.LENGTH_LONG).show();
            }
        });

        binding.loginBtn.setOnClickListener(v -> {
            binding.progressOverlay.setVisibility(View.VISIBLE);
            String email = binding.editTextEmail.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                binding.progressOverlay.setVisibility(View.INVISIBLE);
                return;
            }

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
    }
}
