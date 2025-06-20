package com.managerapp.personnelmanagerapp.presentation.contract.ui;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractListBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.contract.viewmodel.ContractListViewModel;
import com.managerapp.personnelmanagerapp.presentation.contract.adapter.ContractAdapter;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractListFragment extends BaseFragment {
    private FragmentContractListBinding binding;
    private ContractListViewModel viewModel;
    private NavController navController;
    private ContractAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ContractListViewModel.class);
        viewModel.loadAllContracts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContractListBinding.inflate(inflater, container, false);
        setupRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        observeData();
        setupListeners();
    }

    private void setupRecyclerView() {

        adapter = new ContractAdapter(contractId -> {
            ContractListFragmentDirections.ActionContractListFragmentToContractDetailFragment action =
                    ContractListFragmentDirections.actionContractListFragmentToContractDetailFragment(contractId);
            navController.navigate(action);
        });

        binding.recyclerViewContact.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewContact.setAdapter(adapter);
    }

    private void setupListeners() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadAllContracts();
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.backBtn.setOnClickListener(v -> {
            navController.navigate(R.id.action_contractListFragment_to_mainFragment);
            navController.popBackStack();
        });
    }

    private void observeData() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(VISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                binding.progressOverlay.getRoot().setVisibility(GONE);
                List<Contract> contractList = ((UiState.Success<List<Contract>>) state).getData();
                adapter.submitList(contractList);
                if (contractList.isEmpty()) {
                    binding.emptyView.getRoot().setVisibility(VISIBLE);
                } else {
                    binding.emptyView.getRoot().setVisibility(GONE);
                }
            } else if (state instanceof UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                Toast.makeText(requireContext(), ((UiState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
                binding.errorView.getRoot().setVisibility(VISIBLE);
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
