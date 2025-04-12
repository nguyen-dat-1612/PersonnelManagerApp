package com.managerapp.personnelmanagerapp.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractDetailBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.ui.state.ContractDetailState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.ContractDetailViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractDetailFragment extends BaseFragment {

    private static final String ARG_CONTRACT_ID = "contractId";
    private FragmentContractDetailBinding binding;
    private ContractDetailViewModel viewModel;
    private final String TAG = "ContractDetailFragment";
    private int contractId = -1;

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

        if (getArguments() != null) {
            contractId = getArguments().getInt(ARG_CONTRACT_ID, -1); // Giá trị mặc định nếu không có
        }
        if (contractId == -1) {
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
            if (state instanceof  ContractDetailState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.view.setVisibility(INVISIBLE);
            } else if (state instanceof ContractDetailState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                ContractResponse contract = ((ContractDetailState.Success) state).getContract();
                Log.d(TAG, contract.toString());
                binding.setContract(contract);
                binding.txMaHopDong.setText(contract.getId() + "");
                binding.view.setVisibility(VISIBLE);
            } else if (state instanceof ContractDetailState.Error) {
                String errorMessage = ((ContractDetailState.Error) state).getMessage();
                showError(errorMessage);
                binding.view.setVisibility(INVISIBLE);
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