package com.managerapp.personnelmanagerapp.presentation.auth.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentLoginBinding;
import com.managerapp.personnelmanagerapp.presentation.auth.viewmodel.LoginViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.viewmodel.MainViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;
    private boolean isPasswordVisible = false;
    private NavController navController;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        setupListeners();
        observeLoginState();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding.progressOverlay.getRoot().setVisibility(View.INVISIBLE);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
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
            navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        );
    }

    private boolean isInputValid(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showToast(getContext().getString(R.string.please_fill_all_fields));
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


    private void observeLoginState() {
        loginViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                showToast(getContext().getString(R.string.login_success));
                handleLoginSuccess();
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.INVISIBLE);
                showToast(((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void handleLoginSuccess() {
        mainViewModel.loadUserAndRole();
        NavOptions navOptions = new NavOptions.Builder()
                .setPopUpTo(R.id.nav_main, true)
                .build();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            navController.navigate(R.id.action_loginFragment_to_mainFragment, null, navOptions);
        }, 1500);
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}