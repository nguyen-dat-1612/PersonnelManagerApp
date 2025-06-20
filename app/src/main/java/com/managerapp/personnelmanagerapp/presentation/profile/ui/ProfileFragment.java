package com.managerapp.personnelmanagerapp.presentation.profile.ui;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentProfileBinding;
import com.managerapp.personnelmanagerapp.domain.model.DataItem;
import com.managerapp.personnelmanagerapp.presentation.profile.adapter.ProfileAdapter;
import com.managerapp.personnelmanagerapp.presentation.profile.viewmodel.UserViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.utils.ImageLoaderUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private FragmentProfileBinding binding;
    private UserViewModel viewModel;
    private ProfileAdapter profileAdapter;
    private final List<DataItem> userInfoList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.loadUser(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setupRecyclerView();
        loadUserInfo();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupListeners();
    }

    private void setupRecyclerView() {
        profileAdapter = new ProfileAdapter(userInfoList);
        binding.recyclerViewInfomation.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewInfomation.setAdapter(profileAdapter);
        binding.recyclerViewInfomation.setHasFixedSize(true);
    }

    private void setupListeners() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadUser(requireContext());
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
            navController.navigate(R.id.action_profileFragment_to_mainFragment);
            navController.popBackStack();
        });
    }

    private void loadUserInfo() {
        binding.errorView.getRoot().setVisibility(GONE);

        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.content.setVisibility(VISIBLE);
                UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                ImageLoaderUtils.loadAvatar(requireContext(), newUser.getAvatar(), binding.imageUser);
                binding.infoText.setText(
                        getString(
                                R.string.lecturer_info_text,
                                String.valueOf(newUser.getId()),
                                newUser.getDepartmentName()
                        )
                );
                binding.userNameText.setText(newUser.getFullName());
                binding.progressOverlay.getRoot().setVisibility(GONE);
            } else if (state instanceof UiState.Error) {
                String errorMsg = ((UiState.Error) state).getErrorMessage();
                binding.content.setVisibility(GONE);
                binding.errorView.getRoot().setVisibility(VISIBLE);
                binding.progressOverlay.getRoot().setVisibility(GONE);
                Log.e(TAG, "Error loading profile: " + errorMsg);
            }
        });

        viewModel.getUserInfoList().observe(getViewLifecycleOwner(), dataItems -> {
            if (dataItems != null) {
                userInfoList.clear();
                userInfoList.addAll(dataItems);
                profileAdapter.notifyDataSetChanged();
            }
        });
    }
}