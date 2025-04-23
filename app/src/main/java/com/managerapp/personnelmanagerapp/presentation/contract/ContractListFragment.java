package com.managerapp.personnelmanagerapp.presentation.contract;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractListBinding;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.main.MainActivity;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractListFragment extends BaseFragment {

    private static final String TAG = "ContractListFragment";

    private FragmentContractListBinding binding;
    private ContractListViewModel viewModel;
    private NavController navController;
    private ContractAdapter adapter;
    private final List<ContractResponse> contractList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ContractListViewModel.class);
        viewModel.loadAllContracts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContractListBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(requireActivity(), R.id.contract_fragment);
        observeData();
        setupRecyclerView();
        setupListeners();
        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new ContractAdapter(contractList, requireContext(), contractId -> {
            Bundle bundle = new Bundle();
            bundle.putInt("contractId", contractId);
            navController.navigate(R.id.action_contractListFragment_to_contractDetailFragment, bundle);
        });

        binding.recyclerViewContact.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewContact.setAdapter(adapter);
    }

    private void setupListeners() {
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.loadAllContracts());

        binding.backBtn.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    private void observeData() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.emptyView.getRoot().setVisibility(INVISIBLE);
                binding.errorView.getRoot().setVisibility(INVISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                contractList.clear();
                contractList.addAll(((UiState.Success<List<ContractResponse>>) state).getData());
                if (contractList.isEmpty()) {
                    binding.emptyView.getRoot().setVisibility(VISIBLE);
                    binding.recyclerViewContact.setVisibility(INVISIBLE);
                } else {
                    adapter.notifyDataSetChanged();
                    binding.recyclerViewContact.setVisibility(VISIBLE);
                }

            } else if (state instanceof UiState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                Toast.makeText(requireContext(), ((UiState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
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
