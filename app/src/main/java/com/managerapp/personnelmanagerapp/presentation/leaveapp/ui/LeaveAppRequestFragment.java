package com.managerapp.personnelmanagerapp.presentation.leaveapp.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.data.remote.response.LeaveApplicationResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentLeaveAppRequestBinding;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.adapter.LeaveRequestAdapter;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel.LeaveRequestViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.Collections;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;
import com.managerapp.personnelmanagerapp.R;

@AndroidEntryPoint
public class LeaveAppRequestFragment extends Fragment {

    private final String TAG = "LeaveAppRequestActivityMain";
    private FragmentLeaveAppRequestBinding binding;
    private LeaveRequestAdapter adapter;
    private List<LeaveApplicationResponse> leaveRequests;
    private LeaveRequestViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LeaveRequestViewModel.class);
        viewModel.getApplicationIsPending("PENDING");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeaveAppRequestBinding.inflate(inflater, container, false);
        setUpUI();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnListener();
        loadLeaveRequest();
        observeConfirmResult();
    }

    public void setUpUI() {
        adapter = new LeaveRequestAdapter(
                leaveRequest -> {
                    viewModel.confirmApplication(leaveRequest.getId(), "REJECTED");
                },
                leaveRequest -> {
                    viewModel.confirmApplication(leaveRequest.getId(), "APPROVED");
                },
                requireActivity()
        );
        binding.rvLeaveRequests.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvLeaveRequests.setAdapter(adapter);
        binding.rvLeaveRequests.setAdapter(adapter);
    }


    public void setOnListener() {
        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_leaveAppRequestFragment_to_mainFragment);
            navController.popBackStack();
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getApplicationIsPending(binding.spinnerStatus.getSelectedItem().toString());
            binding.swipeRefreshLayout.setRefreshing(false);
        });
        binding.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.getApplicationIsPending(binding.spinnerStatus.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loadLeaveRequest() {
        viewModel.getUiStateLoad().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof UiState.Success) {
                leaveRequests = ((UiState.Success<List<LeaveApplicationResponse>>) state).getData();
                if (leaveRequests.isEmpty()) {
                    binding.viewEmpty.getRoot().setVisibility(VISIBLE);
                    adapter.submitList(Collections.emptyList());
                } else {
                    binding.viewEmpty.getRoot().setVisibility(GONE);
                    adapter.submitList(leaveRequests);
                }
                binding.progressOverlay.getRoot().setVisibility(GONE);
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
                Log.e(TAG, ((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void observeConfirmResult() {
        viewModel.getUiStateConfirm().observe(getViewLifecycleOwner(), result -> {
            if (result instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            }
            else if (result instanceof UiState.Success) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
                binding.viewSendSuccess.getRoot().setVisibility(VISIBLE);
                new Handler().postDelayed(() -> {
                    binding.viewSendSuccess.getRoot().setVisibility(View.INVISIBLE);
                }, 3000);
            } else if (result instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
                Toast.makeText(requireContext(), "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Confirm error: " + ((UiState.Error) result).getErrorMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}