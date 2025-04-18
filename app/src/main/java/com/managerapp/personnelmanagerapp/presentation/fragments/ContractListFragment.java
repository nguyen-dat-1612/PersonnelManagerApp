package com.managerapp.personnelmanagerapp.presentation.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.presentation.activities.ContractActivity;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractListBinding;
import com.managerapp.personnelmanagerapp.presentation.activities.MainActivity;
import com.managerapp.personnelmanagerapp.presentation.adapters.ContractAdapter;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.ContractListViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractListFragment extends BaseFragment {

    private static final String TAG = "ContractListFragment";
    private ContractListViewModel viewModel;
    private FragmentContractListBinding binding;
    private NavController navController;
    private List<ContractResponse> contractList = new ArrayList<>();
    private ContractAdapter adapter;

    public ContractListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentContractListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        navController = Navigation.findNavController(requireActivity(), R.id.contract_fragment);
        viewModel = new ViewModelProvider(this).get(ContractListViewModel.class);
        adapter = new ContractAdapter(contractList, requireContext(), contractId -> {
            Bundle bundle = new Bundle();
            bundle.putInt("contractId", contractId);
            navController.navigate(R.id.action_contractListFragment_to_contractDetailFragment, bundle);
        });

        loadAllContracts();
        binding.swipeRefresh.setOnRefreshListener(() -> {
            loadAllContracts();
        });
        binding.recyclerViewContact.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewContact.setAdapter(adapter);

        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        return view;
    }
    public void loadAllContracts() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(false);
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
            } else if (state instanceof UiState.Success) {
                contractList.clear();
                contractList.addAll(((UiState.Success<List<ContractResponse>>) state).getData());
                if (contractList.isEmpty()) {
                    binding.emptyAnimation.setAnimation(R.raw.empty_animation);
                    binding.emptyText.setText("Danh sách hợp đồng trống");
                    binding.emptyView.setVisibility(VISIBLE);
                    binding.recyclerViewContact.setVisibility(GONE);
                } else {
                    adapter.notifyDataSetChanged();
                    binding.emptyView.setVisibility(GONE);
                    binding.recyclerViewContact.setVisibility(VISIBLE);
                }
            } else if (state instanceof UiState.Error) {
                Toast.makeText(requireContext(), ((UiState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
                binding.emptyView.setVisibility(VISIBLE);

            }
        });
        viewModel.loadAllContracts();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}