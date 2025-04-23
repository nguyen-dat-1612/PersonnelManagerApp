package com.managerapp.personnelmanagerapp.presentation.main;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.domain.model.User;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentProfileBinding;
import com.managerapp.personnelmanagerapp.presentation.changepass.ChangePasswordActivity;
import com.managerapp.personnelmanagerapp.presentation.login.LoginActivity;
import com.managerapp.personnelmanagerapp.presentation.profileInfo.ProfileInfoActivity;
import com.managerapp.personnelmanagerapp.presentation.settings.SettingsActivity;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;


public class ProfileFragment extends BaseFragment {

    private FragmentProfileBinding binding;
    private MainViewModel mainViewModel;
    private final String TAG = "ProfileFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.loadUserAndRole();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        loadUserInfo();
        setupClickListeners();
        return binding.getRoot();
    }

    private void setupClickListeners() {
        binding.btnSettings.setOnClickListener(v -> startActivity(new Intent(requireContext(), SettingsActivity.class)));
        binding.btnProfileInfo.setOnClickListener(v -> startActivity(new Intent(requireContext(), ProfileInfoActivity.class)));
        binding.btnChangePassword.setOnClickListener(v -> startActivity(new Intent(requireContext(), ChangePasswordActivity.class)));

        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void loadUserInfo() {
        UserProfileResponse user = ((MainActivity) requireActivity()).getCurrentUser();
        if (user == null) {
            mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {

                if (state instanceof UiState.Loading) {

                }
                else if (state instanceof UiState.Success) {
                    UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                    ((MainActivity) requireActivity()).setUser(newUser);

                    Glide.with(requireContext())
                            .load(newUser.getAvatar())
                            .placeholder(R.drawable.ic_default_avatar)
                            .error(R.drawable.ic_broken_image)
                            .into(binding.imageUser);
                    binding.magvtext.setText("Mã giảng viên: " + user.getId());
                    binding.khoagvtext.setText("Khoa: " + user.getDepartmentName());
                    binding.userNameText.setText(newUser.getFullName());
                    binding.main.setVisibility(View.VISIBLE);
                }
                else if (state instanceof UiState.Error) {
                    String errorMsg = ((UiState.Error) state).getErrorMessage();
                    Log.e(TAG, "Error loading profile: " + errorMsg);
                }
            });
        } else {
            binding.magvtext.setText("Mã giảng viên: " + user.getId());
            binding.khoagvtext.setText("Khoa: " + user.getDepartmentName());
            Glide.with(requireContext())
                    .load(user.getAvatar())
                    .placeholder(R.drawable.ic_default_avatar) // Ảnh mặc định khi đang tải
                    .error(R.drawable.ic_broken_image)
                    .into(binding.imageUser);
            binding.userNameText.setText(user.getFullName());
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}