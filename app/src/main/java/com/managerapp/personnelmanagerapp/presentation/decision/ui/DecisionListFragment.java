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
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentDecisionListBinding;
import com.managerapp.personnelmanagerapp.domain.model.Decision;
import com.managerapp.personnelmanagerapp.presentation.decision.state.DecisionState;
import com.managerapp.personnelmanagerapp.presentation.decision.viewmodel.DecisionListViewModel;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.DecisionAdapter;

import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DecisionListFragment extends Fragment {
    private FragmentDecisionListBinding binding;
    private DecisionAdapter adapter;
    private DecisionListViewModel viewModel;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DecisionListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDecisionListBinding.inflate(inflater, container, false);
        setupRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        setOnClicks();
        loadData();
    }

    private void setupRecyclerView() {
        adapter = new DecisionAdapter(decisionId -> {
            DecisionListFragmentDirections.ActionDecisionListFragmentToDecisionDetailFragment action =
                    DecisionListFragmentDirections.actionDecisionListFragmentToDecisionDetailFragment(decisionId);
            navController.navigate(action);
        });
        binding.recyclerViewDecision.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewDecision.setAdapter(adapter);
    }
    private void setOnClicks() {
        binding.backBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_decisionListFragment_to_mainFragment);
            navController.popBackStack();

        });
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refreshDecisions();
            binding.swipeRefresh.setRefreshing(false);
        });
    }


    private void loadData() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            binding.progressOverlay.getRoot().setVisibility(
                    state.isLoading() ? View.VISIBLE : View.GONE
            );

            if (state.getDecisions() != null) {
                List<Decision> descistions = state.getDecisions();
                if (state.getDecisions().isEmpty()) {
                    binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                } else {
                    adapter.submitList(descistions);
                    updateStats(state.getStats());
                }
            }

            if (state.getErrorMessage() != null) {
                binding.errorView.getRoot().setVisibility(View.VISIBLE);
                Toast.makeText(requireContext(), state.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStats(DecisionState stats) {
        binding.awardCount.setText(String.valueOf(stats.awardCount));
        binding.disciplineCount.setText(String.valueOf(stats.disciplineCount));
        binding.promotionCount.setText(String.valueOf(stats.promotionCount));
        binding.salaryIncreaseCount.setText(String.valueOf(stats.salaryIncreaseCount));
        binding.seniorityCount.setText(String.valueOf(stats.seniorityCount));
        binding.terminationCount.setText(String.valueOf(stats.terminationCount));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}