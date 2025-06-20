package com.managerapp.personnelmanagerapp.presentation.leaveapp.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentLeaveAppRequestBinding;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.LeaveApplication;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.adapter.LeaveRequestAdapter;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel.LeaveRequestViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LeaveAppRequestFragment extends Fragment {

    private FragmentLeaveAppRequestBinding binding;
    private LeaveRequestAdapter leaveRequestAdapter;
    private List<LeaveApplication> leaveRequests;
    private LeaveRequestViewModel viewModel;

    private FormStatusEnum formStatusEnum;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LeaveRequestViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    private void setUpUI() {
        leaveRequestAdapter = new LeaveRequestAdapter(
                leaveRequest -> viewModel.confirmApplication(leaveRequest.getId(), FormStatusEnum.REJECTED),
                leaveRequest -> viewModel.confirmApplication(leaveRequest.getId(), FormStatusEnum.APPROVED),
                requireActivity()
        );

        binding.rvLeaveRequests.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvLeaveRequests.setAdapter(leaveRequestAdapter);

        ArrayAdapter<FormStatusEnum> statusAdapter = new ArrayAdapter<FormStatusEnum>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                FormStatusEnum.values()
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setText(getContext().getString(getItem(position).getLocalizedStringRes()));
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setText(getContext().getString(getItem(position).getLocalizedStringRes()));
                return view;
            }
        };

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStatus.setAdapter(statusAdapter);
    }

    private void setOnListener() {
        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_leaveAppRequestFragment_to_mainFragment);
            navController.popBackStack();
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.getApplicationIsPending(formStatusEnum);
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        binding.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formStatusEnum = (FormStatusEnum) binding.spinnerStatus.getSelectedItem();
                viewModel.getApplicationIsPending(formStatusEnum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No-op
            }
        });
    }

    private void loadLeaveRequest() {
        viewModel.getUiStateLoad().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof UiState.Success) {
                leaveRequests = ((UiState.Success<List<LeaveApplication>>) state).getData();
                binding.rvLeaveRequests.postDelayed(() -> {
                    if (leaveRequests.isEmpty()) {
                        binding.viewEmpty.getRoot().setVisibility(VISIBLE);
                        leaveRequestAdapter.submitList(Collections.emptyList());
                    } else {
                        binding.viewEmpty.getRoot().setVisibility(GONE);
                        leaveRequestAdapter.submitList(leaveRequests);
                    }
                    binding.progressOverlay.getRoot().setVisibility(GONE);
                }, 300);
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
            }
        });
    }

    private void observeConfirmResult() {
        viewModel.getUiStateConfirm().observe(getViewLifecycleOwner(), result -> {
            if (result instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            } else if (result instanceof UiState.Success) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
                binding.viewSendSuccess.getRoot().setVisibility(VISIBLE);
                new Handler().postDelayed(() -> binding.viewSendSuccess.getRoot().setVisibility(View.INVISIBLE), 3000);
            } else if (result instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
