package com.managerapp.personnelmanagerapp.presentation.decision.ui;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentApproveDecisionBinding;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.DecisionAdapter;
import com.managerapp.personnelmanagerapp.presentation.decision.viewmodel.ApproveDecisionViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ApproveDecisionFragment extends Fragment {
    private FragmentApproveDecisionBinding binding;
    private ApproveDecisionViewModel viewModel;
    private DecisionAdapter adapter;
    private NavController navController;
    private DecisionEnum[] decisionEnums = DecisionEnum.values();
    private List<String> displayNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ApproveDecisionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentApproveDecisionBinding.inflate(inflater, container, false);
        setupView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeView();
        onListener();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    private void setupView() {
        adapter = new DecisionAdapter(decisionId -> {
            ApproveDecisionFragmentDirections.ActionApproveDecisionFragmentToDetailApproveFragment action =
                    ApproveDecisionFragmentDirections.actionApproveDecisionFragmentToDetailApproveFragment(decisionId);
            navController.navigate(action);
        });
        binding.recyclerViewDecision.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewDecision.setAdapter(adapter);

        displayNames.clear();
        for (DecisionEnum decision : decisionEnums) {
            displayNames.add(getString(decision.getStringRes()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                displayNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerType.setAdapter(adapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.select_type_decision_status,
                android.R.layout.simple_spinner_item
        );
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStatus.setAdapter(statusAdapter);

    }

    private void onListener() {
        binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DecisionEnum selectedEnum = decisionEnums[position];
                viewModel.setSelectedType(selectedEnum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean isApproved = (position == 1);
                viewModel.setSelectedStatus(isApproved);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        binding.backBtn.setOnClickListener(v -> {
            navController.popBackStack();
        });
    }

    private void observeView() {
        viewModel.getGetDecisionUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof  UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                List<Decision> decisions = ((UiState.Success<List<Decision>>) state).getData();
                if (decisions.isEmpty()) {
                    binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.emptyView.getRoot().setVisibility(View.GONE);
                }
                adapter.submitList(decisions);
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.errorView.getRoot().setVisibility(View.VISIBLE);
                Log.e("ApproveDecisionFragment", "Error loading decisions: " + ((UiState.Error) state).getErrorMessage());
            }
        });
    }
}