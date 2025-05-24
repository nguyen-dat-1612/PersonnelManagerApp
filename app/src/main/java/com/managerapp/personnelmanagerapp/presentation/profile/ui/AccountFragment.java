package com.managerapp.personnelmanagerapp.presentation.profile.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentAccountBinding;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.main.viewmodel.MainViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountFragment extends BaseFragment {

    private FragmentAccountBinding binding;
    private MainViewModel mainViewModel;
    private final String TAG = "ProfileFragment";
    private NavController navController;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        loadUserInfo();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.btnSettings.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_settingsFragment);
        });

        binding.btnProfileInfo.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_profileFragment);
        });
        binding.btnChangePassword.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_changePasswordFragment);
        });

        binding.btnLogout.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.fade_in)
                    .setPopExitAnim(R.anim.fade_out)
                    .setPopUpTo(R.id.nav_main, true)  // Xóa hết back stack
                    .build();

            navController.navigate(
                    R.id.action_mainFragment_to_loginFragment,
                    null,
                    navOptions
            );
        });
    }

    private void loadUserInfo() {
        mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {

            }
            else if (state instanceof UiState.Success) {
                UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                Glide.with(requireContext())
                        .load(newUser.getAvatar())
                        .placeholder(R.drawable.ic_default_avatar)
                        .error(R.drawable.ic_broken_image)
                        .into(binding.imageUser);
                binding.magvtext.setText("Mã giảng viên: " + newUser.getId());
                binding.khoagvtext.setText("Khoa: " + newUser.getDepartmentName());
                binding.userNameText.setText(newUser.getFullName());
                binding.main.setVisibility(View.VISIBLE);
            }
            else if (state instanceof UiState.Error) {
                String errorMsg = ((UiState.Error) state).getErrorMessage();
                Log.e(TAG, "Error loading profile: " + errorMsg);
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Cha hieu kieu gi", "onDestroy: ");
    }
}