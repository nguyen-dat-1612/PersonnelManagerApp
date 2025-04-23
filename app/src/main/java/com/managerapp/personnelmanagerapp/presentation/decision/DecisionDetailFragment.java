package com.managerapp.personnelmanagerapp.presentation.decision;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentDecisionDetailBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DecisionDetailFragment extends Fragment {

    private FragmentDecisionDetailBinding binding;
    private final String TAG = "DecisionDetailFragment";
    private String decisionId;
    private DecisionDetailViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DecisionDetailViewModel.class);
        // Retrieve decisionId from arguments
        decisionId = getArguments() != null ? getArguments().getString("decisionId") : null;
        if (decisionId != null) {
            Log.d(TAG, "Decision ID: " + decisionId);
        } else {
            Log.e(TAG, "Decision ID is null");
        }
        viewModel.fetchDecisionById(decisionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDecisionDetailBinding.inflate(inflater, container, false);
        loadData();
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.fetchDecisionById(decisionId));
        return binding.getRoot();
    }

    public void loadData() {
        binding.errorView.getRoot().setVisibility(INVISIBLE);
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.mainContent.setVisibility(INVISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                DecisionResponse decision = ((UiState.Success<DecisionResponse>) state).getData();
                if (decision != null) {
                    binding.setDecision(decision);
                    binding.mainContent.setVisibility(VISIBLE);
                } else {
                    binding.errorView.getRoot().setVisibility(VISIBLE);
                    binding.mainContent.setVisibility(INVISIBLE);
                }
            } else if (state instanceof UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                binding.mainContent.setVisibility(INVISIBLE);

                binding.errorView.getRoot().setVisibility(VISIBLE);
            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}