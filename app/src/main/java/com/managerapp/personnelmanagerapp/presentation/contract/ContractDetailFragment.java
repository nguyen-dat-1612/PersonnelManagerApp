package com.managerapp.personnelmanagerapp.presentation.contract;

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
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractDetailBinding;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractDetailFragment extends BaseFragment {

    private static final String ARG_CONTRACT_ID = "contractId";
    private FragmentContractDetailBinding binding;
    private ContractDetailViewModel viewModel;
    private final String TAG = "ContractDetailFragment";
    private int contractId = -1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ContractDetailViewModel.class);

        if (getArguments() != null) {
            contractId = getArguments().getInt(ARG_CONTRACT_ID, -1); // Giá trị mặc định nếu không có
        }
        if (contractId == -1) {
            showErrorAndFinish("Không tìm thấy hợp đồng!");
        }
        viewModel.loadContractById(contractId);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentContractDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        observeViewModel();
    }

    private void setupViews() {
        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack();
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadContractById(contractId);
        });
    }

    private void observeViewModel() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof  UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.mainView.setVisibility(INVISIBLE);
                binding.errorView.getRoot().setVisibility(INVISIBLE);
            } else if (state instanceof UiState.Success) {
                ContractResponse contract = ((UiState.Success<ContractResponse>) state).getData();
                binding.swipeRefresh.setRefreshing(false);
                binding.setContract(contract);
                binding.mainView.setVisibility(VISIBLE);
            } else if (state instanceof UiState.Error) {
                String errorMessage = ((UiState.Error) state).getErrorMessage();
                showError(errorMessage);
                binding.errorView.getRoot().setVisibility(VISIBLE);
                binding.mainView.setVisibility(INVISIBLE);
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