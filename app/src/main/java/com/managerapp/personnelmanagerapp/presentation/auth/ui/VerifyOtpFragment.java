package com.managerapp.personnelmanagerapp.presentation.auth.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentVerifyOtpBinding;
import com.managerapp.personnelmanagerapp.presentation.auth.viewmodel.ForgotPasswordViewModel;
import com.managerapp.personnelmanagerapp.presentation.auth.state.ForgotPasswordState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VerifyOtpFragment extends BaseFragment {
    private FragmentVerifyOtpBinding binding;
    private ForgotPasswordViewModel viewModel;
    private String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVerifyOtpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        email = VerifyOtpFragmentArgs.fromBundle(getArguments()).getEmail();
        viewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);

        setupOtpInputs();
        setupListeners();
        setupObservers();

        binding.otp1.postDelayed(() -> {
            binding.otp1.requestFocus();
            showKeyboard();
        }, 100);
    }

    private void setupObservers() {
        viewModel.getForgotPasswordState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof ForgotPasswordState.Loading) {
                showLoading(true);
            } else if (state instanceof ForgotPasswordState.OtpVerified) {
                showLoading(false);
                navigateToResetPassword();
            } else if (state instanceof ForgotPasswordState.Error) {
                showLoading(false);
                showToast(((ForgotPasswordState.Error) state).getErrorMessage());
                clearOtpFields();
                binding.otp1.requestFocus();
                showKeyboard();
            }
        });
    }

    private void setupListeners() {
        binding.verifyButton.setOnClickListener(v -> {
            String otp = getOtp();
            if (otp.length() == 6) {
                if (this.email == null) {
                    showToast(getContext().getString(R.string.error_send_forgot_password));
                    return;
                }
                viewModel.verifyOtp(otp);
            } else {
                showToast(getContext().getString(R.string.otp_enter_6_digits));
                binding.otp1.requestFocus();
                showKeyboard();
            }
        });

        binding.resendOtp.setOnClickListener(v -> resendOtp());
    }

    private void setupOtpInputs() {
        TextWatcher otpTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                View current = requireActivity().getCurrentFocus();

                if (count == 1 && current != null) {
                    if (current == binding.otp1) {
                        binding.otp2.requestFocus();
                    } else if (current == binding.otp2) {
                        binding.otp3.requestFocus();
                    } else if (current == binding.otp3) {
                        binding.otp4.requestFocus();
                    } else if (current == binding.otp4) {
                        binding.otp5.requestFocus();
                    } else if (current == binding.otp5) {
                        binding.otp6.requestFocus();
                    } else if (current == binding.otp6) {
                        hideKeyboard();
                    }
                }

                if (count == 0 && before == 1 && current != null) {
                    if (current == binding.otp6) {
                        binding.otp5.requestFocus();
                    } else if (current == binding.otp5) {
                        binding.otp4.requestFocus();
                    } else if (current == binding.otp4) {
                        binding.otp3.requestFocus();
                    } else if (current == binding.otp3) {
                        binding.otp2.requestFocus();
                    } else if (current == binding.otp2) {
                        binding.otp1.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 1) {
                    handlePastedOtp(s.toString());
                    s.clear();
                }
            }
        };

        binding.otp1.addTextChangedListener(otpTextWatcher);
        binding.otp2.addTextChangedListener(otpTextWatcher);
        binding.otp3.addTextChangedListener(otpTextWatcher);
        binding.otp4.addTextChangedListener(otpTextWatcher);
        binding.otp5.addTextChangedListener(otpTextWatcher);
        binding.otp6.addTextChangedListener(otpTextWatcher);

        binding.otp1.setOnLongClickListener(v -> {
            handlePasteFromClipboard();
            return true;
        });
    }

    private String getOtp() {
        return binding.otp1.getText().toString() +
                binding.otp2.getText().toString() +
                binding.otp3.getText().toString() +
                binding.otp4.getText().toString() +
                binding.otp5.getText().toString() +
                binding.otp6.getText().toString();
    }

    private void handlePastedOtp(String otp) {
        if (otp.length() >= 6) {
            binding.otp1.setText(otp.substring(0, 1));
            binding.otp2.setText(otp.substring(1, 2));
            binding.otp3.setText(otp.substring(2, 3));
            binding.otp4.setText(otp.substring(3, 4));
            binding.otp5.setText(otp.substring(4, 5));
            binding.otp6.setText(otp.substring(5, 6));
            binding.otp6.requestFocus();
            hideKeyboard();
        }
    }

    private void handlePasteFromClipboard() {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            if (item != null && item.getText() != null) {
                String pasteData = item.getText().toString();
                if (pasteData.matches("\\d{6}")) {
                    handlePastedOtp(pasteData);
                } else {
                    showToast(getContext().getString(R.string.otp_code_6_digits));
                }
            }
        }
    }

    private void resendOtp() {
        viewModel.sendForgotPassword(email);
        clearOtpFields();
        showToast(getContext().getString(R.string.otp_resent));
        binding.otp1.requestFocus();
        showKeyboard();
    }

    private void navigateToResetPassword() {
        NavDirections action = VerifyOtpFragmentDirections
                .actionVerifyOtpFragmentToResetPasswordFragment(email);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }

    private void clearOtpFields() {
        binding.otp1.setText("");
        binding.otp2.setText("");
        binding.otp3.setText("");
        binding.otp4.setText("");
        binding.otp5.setText("");
        binding.otp6.setText("");
    }

    private void showLoading(boolean isLoading) {
        binding.progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.verifyButton.setEnabled(!isLoading);
        binding.resendOtp.setEnabled(!isLoading);
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(binding.otp1, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}