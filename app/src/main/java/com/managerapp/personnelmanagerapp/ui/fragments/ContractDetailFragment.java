package com.managerapp.personnelmanagerapp.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractDetailBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.ui.state.ContractDetailState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.ContractDetailViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractDetailFragment extends Fragment {

    private static final String ARG_CONTRACT_ID = "contractId";
    private FragmentContractDetailBinding binding;
    private ContractDetailViewModel viewModel;
    private String contractId;

    public ContractDetailFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentContractDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ContractDetailViewModel.class);
        contractId = getArguments() != null ? getArguments().getString(ARG_CONTRACT_ID) : null;

        if (contractId == null) {
            showErrorAndFinish("Không tìm thấy hợp đồng!");
            return;
        }

        setupViews();
        observeViewModel();
        viewModel.loadContractById(contractId);
    }

    private void setupViews() {
        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                requireActivity().onBackPressed();
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadContractById(contractId);
        });
    }

    private void observeViewModel() {
        viewModel.getContractDetailState().observe(getViewLifecycleOwner(), state -> {
            binding.swipeRefresh.setRefreshing(state instanceof ContractDetailState.Loading);

            if (state instanceof ContractDetailState.Success) {
                Contract contract = ((ContractDetailState.Success) state).getContract();
                binding.setContract(contract);
//                binding.emptyView.setVisibility(GONE);
                binding.view.setVisibility(VISIBLE);
            } else if (state instanceof ContractDetailState.Error) {
                String errorMessage = ((ContractDetailState.Error) state).getMessage();
                showError(errorMessage);
//                binding.emptyView.setVisibility(VISIBLE);
                binding.view.setVisibility(GONE);
            }
        });
    }

    private void showError(@NonNull String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorAndFinish(@NonNull String message) {
        showError(message);
        requireActivity().onBackPressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}