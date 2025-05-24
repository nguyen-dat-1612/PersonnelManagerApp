package com.managerapp.personnelmanagerapp.presentation.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.presentation.main.viewmodel.MainViewModel;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentHomeBinding;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.domain.model.Role;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";
    private MainViewModel mainViewModel;
    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.loadUserAndRole();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        loadUserInfo();
        setupListeners();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    private void setupListeners() {
        binding.logoutBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_loginFragment);
        });

        binding.YeucauBtn.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_requestHistoryFragment)
        );
        binding.btnFeedBack.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_feedBackFragment)
        );

        binding.btnContracts.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_contractListFragment)
        );

        binding.btnRewardAndDiscipline.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_decisionListFragment)
        );

        binding.btnSendNotification.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_sendNotificationFragment)
        );

        binding.btnCongTac.setOnClickListener(v -> {
            navController.navigate(R.id.action_mainFragment_to_workLogFragment);
        });
        binding.DuyetCardView.setOnClickListener(v ->
            navController.navigate(R.id.action_mainFragment_to_leaveAppRequestFragment)
        );

    }


    private void loadUserInfo() {
        mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Success) {
                UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                updateUI(newUser);
            } else if (state instanceof UiState.Error) {
                Log.e(TAG, "Error loading profile: " + ((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void updateUI(UserProfileResponse user) {
        binding.numRewardText.setText(String.valueOf(user.getNumReward()));
        binding.numDisciplineText.setText(String.valueOf(user.getNumDiscipline()));
        binding.positionNameText.setText("Chức vụ: " + user.getPositionName());
        binding.departmentNameText.setText("Nơi làm việc : " + user.getDepartmentName());
        binding.serviceDurationText.setText(String.valueOf(user.getServiceDuration()));
        loadAvatar(user);

        String roleStr = mainViewModel.getRole(); // VD: "MANAGER"
        Role role = Role.fromString(roleStr);

        if (Role.MANAGER.equals(role)) {
            binding.DuyetCardView.setVisibility(VISIBLE);
        } else {
            binding.DuyetCardView.setVisibility(GONE);
        }
    }
    private void loadAvatar(UserProfileResponse user) {
        Log.d("AVATAR_URL", "Avatar URL:" + user.getAvatar() +".end");

        Glide.with(requireContext())
                .load(user.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_broken_image)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // Log lỗi chi tiết
                        if (e != null) {
                            Log.e("Glide", "Error loading image: " + e.getMessage());
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(binding.imageUser);
    }

    @Override
    public void onDestroyView() {
        binding.logoutBtn.setOnClickListener(null);
        binding.YeucauBtn.setOnClickListener(null);
        binding.btnFeedBack.setOnClickListener(null);
        binding.btnContracts.setOnClickListener(null);
        binding.btnRewardAndDiscipline.setOnClickListener(null);
        binding.btnSendNotification.setOnClickListener(null);
        binding.cardViewCollaborate.setOnClickListener(null);

        binding = null;
        super.onDestroyView();
    }

}
