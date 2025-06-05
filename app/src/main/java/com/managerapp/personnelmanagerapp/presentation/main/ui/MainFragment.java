package com.managerapp.personnelmanagerapp.presentation.main.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentMainBinding;
import com.managerapp.personnelmanagerapp.presentation.main.viewmodel.MainViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private NavController navController;
    private FragmentMainBinding binding;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.loadUserAndRole();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_bottom);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            Log.d(TAG, "NavController initialized");
        } else {
            Log.e(TAG, "NavHostFragment is null");
        }
        setupBottomNavigation(savedInstanceState);
        loadUserInfo();

        if (mainViewModel.isDataLoaded()) {
            binding.loadingLayout.setVisibility(GONE);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finishAffinity();
            }
        });
    }

    private void setupBottomNavigation(Bundle savedInstanceState) {
        if (binding.bottomBar == null) {
            Log.e(TAG, "BottomBar is null");
            return;
        }

        // Điều hướng đến Home fragment khi lần đầu (savedInstanceState null)
        if (savedInstanceState == null && navController != null) {
            if (navController.getCurrentDestination() == null) {
                navController.navigate(R.id.home_fragment);
                binding.bottomBar.setItemSelected(R.id.home_fragment, true);
            } else {
                binding.bottomBar.setItemSelected(navController.getCurrentDestination().getId(), true);
            }
        }

        binding.bottomBar.setOnItemSelectedListener(id -> {
            if (navController == null) return;

            // Nếu đã ở fragment đó rồi thì bỏ qua
            if (navController.getCurrentDestination() != null &&
                    navController.getCurrentDestination().getId() == id) {
                return;
            }
            navController.navigate(id);
        });
    }


    private void loadUserInfo() {
        mainViewModel.getRoleUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Success) {
//                if (mainViewModel.isDataLoaded()) {
//                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
//                        binding.loadingLayout.setVisibility(VISIBLE);
//                        binding.main.setVisibility(VISIBLE);
//                        binding.loadingLayout.animate()
//                                .translationX(-binding.loadingLayout.getWidth())
//                                .setDuration(400)
//                                .start();
//                    }, 500);
//                }
            } else if (state instanceof UiState.Error) {
                Log.e(TAG, "Error loading profile: " + ((UiState.Error) state).getErrorMessage());
            }
        });
    }
}
