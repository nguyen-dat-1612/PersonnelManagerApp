package com.managerapp.personnelmanagerapp.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.lifecycle.ViewModelProvider;

import com.managerapp.personnelmanagerapp.presentation.forgetpass.ForgotPasswordActivity;
import com.managerapp.personnelmanagerapp.presentation.main.MainActivity;
import com.managerapp.personnelmanagerapp.presentation.base.BaseActivity;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.ActivityLoginBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private boolean isPasswordVisible = false;
    private static final String TAG = "LoginActivityLifecycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.progressOverlay.getRoot().setVisibility(View.INVISIBLE);

        setupListeners();
        observeLoginState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void setupListeners() {
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();
            if (!isInputValid(email, password)) return;

            binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            loginViewModel.login(email, password);
        });

        binding.passwordToggle.setOnClickListener(v -> togglePasswordVisibility());

        binding.btnForgotPassword.setOnClickListener(v ->
                startActivity(new Intent(this, ForgotPasswordActivity.class)));
    }

    private boolean isInputValid(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast("Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        return true;
    }

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        int inputType = isPasswordVisible
                ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        binding.editTextPassword.setInputType(inputType);
        binding.passwordToggle.setImageResource(
                isPasswordVisible ? R.drawable.ic_eye_on : R.drawable.ic_eye_off
        );
        binding.editTextPassword.setSelection(binding.editTextPassword.getText().length());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void observeLoginState() {
        loginViewModel.getUiState().observe(this, state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                handleLoginSuccess();
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.INVISIBLE);
                handleLoginError(((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void handleLoginSuccess() {
        showToast("Đăng nhập thành công!");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void handleLoginError(String errorMessage) {
        showToast("Đăng nhập thất bại: " + errorMessage);
    }
}