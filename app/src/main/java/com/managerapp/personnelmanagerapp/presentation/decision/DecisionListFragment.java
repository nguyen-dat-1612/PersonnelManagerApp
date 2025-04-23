package com.managerapp.personnelmanagerapp.presentation.decision;

import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentDecisionListBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DecisionListFragment extends Fragment {

    private final String TAG = "DecisionListFragment";

    private FragmentDecisionListBinding binding;
    private DecisionAdapter adapter;
    private DecisionListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DecisionListViewModel.class);
        viewModel.fetchDecisions();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDecisionListBinding.inflate(inflater, container, false);
        setOnClicks();
        loadData();
        return binding.getRoot();
    }

    private void setOnClicks() {
        binding.backBtn.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.fetchDecisions();
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void loadData() {
        binding.emptyView.getRoot().setVisibility(View.INVISIBLE);
        binding.errorView.getRoot().setVisibility(View.INVISIBLE);

        binding.recyclerViewDecision.setVisibility(View.INVISIBLE);

        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
            } else if (state instanceof UiState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                List<DecisionResponse> decisions = ((UiState.Success<List<DecisionResponse>>) state).getData();
                updateStats(decisions);
                if (decisions.isEmpty()) {
                    binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                } else {
                    adapter = new DecisionAdapter(decisions, decisionId -> {
                        Bundle args = new Bundle();
                        args.putString("decisionId", decisionId);
                        Navigation.findNavController(requireActivity(), R.id.fragmentContainer)
                                .navigate(R.id.action_decisionListFragment_to_decisionDetailFragment, args);
                    });
                    binding.recyclerViewDecision.setLayoutManager(new LinearLayoutManager(requireContext()));
                    binding.recyclerViewDecision.setAdapter(adapter);
                    binding.recyclerViewDecision.setVisibility(VISIBLE);
                }
            } else if (state instanceof UiState.Error) {
                binding.errorView.getRoot().setVisibility(VISIBLE);
                Log.e(TAG, "Đã có lỗi xảy ra: " + ((UiState.Error) state).getErrorMessage());
            }
        });
    }

    private void updateStats(List<DecisionResponse> decisions) {
        int awardCount = 0;
        int disciplineCount = 0;
        int promotionCount = 0;
        int salaryIncreaseCount = 0;
        int seniorityCount = 0;
        int terminationCount = 0;

        for (DecisionResponse decision : decisions) {
            if (decision.getType() != null) {
                switch (decision.getType().toUpperCase()) {
                    case "AWARD":
                        awardCount++;
                        break;
                    case "DISCIPLINE":
                        disciplineCount++;
                        break;
                    case "PROMOTION":
                        promotionCount++;
                        break;
                    case "INCREASE_SALARY":
                        salaryIncreaseCount++;
                        break;
                    case "SENIORITY_ALLOWANCE":
                        seniorityCount++;
                        break;
                    case "TERMINATION":
                        terminationCount++;
                        break;
                }
            }
        }

        binding.awardCount.setText(String.valueOf(awardCount));
        binding.disciplineCount.setText(String.valueOf(disciplineCount));
        binding.promotionCount.setText(String.valueOf(promotionCount));
        binding.salaryIncreaseCount.setText(String.valueOf(salaryIncreaseCount));
        binding.seniorityCount.setText(String.valueOf(seniorityCount));
        binding.terminationCount.setText(String.valueOf(terminationCount));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}