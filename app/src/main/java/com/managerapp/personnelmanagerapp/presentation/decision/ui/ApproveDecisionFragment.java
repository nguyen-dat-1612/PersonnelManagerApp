package com.managerapp.personnelmanagerapp.presentation.decision.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentApproveDecisionBinding;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.DecisionAdapter;
import com.managerapp.personnelmanagerapp.presentation.decision.viewmodel.ApproveDecisionViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ApproveDecisionFragment extends Fragment {

    private FragmentApproveDecisionBinding binding;
    private ApproveDecisionViewModel viewModel;
    private DecisionAdapter adapter;

    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ApproveDecisionViewModel.class);
        viewModel.getAllDecisions("AWARD", true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentApproveDecisionBinding.inflate(inflater, container, false);
        setupRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeView();
        onListener();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    private void setupRecyclerView() {
        adapter = new DecisionAdapter(decisionId -> {
            ApproveDecisionFragmentDirections.ActionApproveDecisionFragmentToDetailApproveFragment action =
                    ApproveDecisionFragmentDirections.actionApproveDecisionFragmentToDetailApproveFragment(decisionId);
            navController.navigate(action);
        });
        binding.recyclerViewDecision.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewDecision.setAdapter(adapter);

    }

    private void onListener() {
        binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                boolean approve = binding.spinnerStatus.getSelectedItemPosition() == 1; // đúng ở đây
                viewModel.getAllDecisions(selected, approve);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = binding.spinnerType.getSelectedItem().toString();
                boolean approve = binding.spinnerStatus.getSelectedItemPosition() == 1; // phải giữ nguyên
                viewModel.getAllDecisions(selectedType, approve);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
        });
    }

    private void observeView() {
        viewModel.getGetDecisionUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof  UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                adapter.submitList(((UiState.Success<List<DecisionResponse>>) state).getData());
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.errorView.getRoot().setVisibility(View.VISIBLE);
            }
        });
    }
}