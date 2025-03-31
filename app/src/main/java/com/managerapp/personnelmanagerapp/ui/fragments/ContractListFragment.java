package com.managerapp.personnelmanagerapp.ui.fragments;

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

import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractListBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.ui.activities.MainActivity;
import com.managerapp.personnelmanagerapp.ui.adapters.ContractAdapter;
import com.managerapp.personnelmanagerapp.ui.state.ContractListState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.ContractListViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractListFragment extends BaseFragment {

    private static final String TAG = "ContractListFragment";
    private ContractListViewModel viewModel;
    private FragmentContractListBinding binding;
    private NavController navController;
    private List<Contract> contractList = new ArrayList<>();
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
            bundle.putString("contractId", contractId);
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
        viewModel.getContractState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(false);
            if (state instanceof ContractListState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
            } else if (state instanceof ContractListState.Success) {
                contractList.clear();
                contractList.addAll(((ContractListState.Success) state).contracts);
                adapter.notifyDataSetChanged();
                binding.emptyView.setVisibility(GONE);
                binding.recyclerViewContact.setVisibility(VISIBLE);
            } else if (state instanceof ContractListState.Error) {
                Toast.makeText(requireContext(), ((ContractListState.Error) state).message, Toast.LENGTH_SHORT).show();
                binding.emptyView.setVisibility(VISIBLE);

            } else if (state instanceof ContractListState.Empty) {
                binding.emptyAnimation.setAnimation(R.raw.empty_animation);
                binding.emptyText.setText("Danh sách hợp đồng trống");
                binding.emptyView.setVisibility(VISIBLE);
                binding.recyclerViewContact.setVisibility(GONE);
            }
        });
        viewModel.loadAllContracts(1);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}