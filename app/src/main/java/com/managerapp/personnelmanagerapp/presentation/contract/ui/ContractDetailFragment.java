package com.managerapp.personnelmanagerapp.presentation.contract.ui;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractDetailBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.contract.viewmodel.ContractDetailViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.utils.WebViewUtils;
import java.io.UnsupportedEncodingException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractDetailFragment extends BaseFragment {
    private FragmentContractDetailBinding binding;
    private ContractDetailViewModel viewModel;
    private int contractId = -1;
    private String pdfUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ContractDetailViewModel.class);

        int contractId = ContractDetailFragmentArgs.fromBundle(getArguments()).getContractId();
        if (contractId == -1) {
            showErrorAndFinish(getString(R.string.error_invalid_contract_id));
            return;
        }
        this.contractId = contractId;
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
            navController.navigate(R.id.action_contractDetailFragment_to_contractListFragment);
            navController.popBackStack();
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.loadContractById(contractId);
        });

    }

    private void observeViewModel() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.mainView.setVisibility(INVISIBLE);
                binding.errorView.getRoot().setVisibility(INVISIBLE);
            } else if (state instanceof UiState.Success) {
                Contract contract = ((UiState.Success<Contract>) state).getData();
                binding.swipeRefresh.setRefreshing(false);
                binding.setContract(contract);
                binding.txStatus.setText(contract.getContractStatusEnum().getIconWithText(requireContext()));
                binding.mainView.setVisibility(VISIBLE);

                pdfUrl = "https://www.googleapis.com/drive/v3/files/" + contract.getClause() +
                        "?alt=media&key=AIzaSyB3sNzITjsRHLvRq68dECM-w8N2tS0ZznQ";
                configureWebViewSettings();
                loadPdf(pdfUrl);
            } else if (state instanceof UiState.Error) {
                String errorMessage = ((UiState.Error) state).getErrorMessage();
                showError(errorMessage);
                binding.errorView.getRoot().setVisibility(VISIBLE);
                binding.mainView.setVisibility(INVISIBLE);
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        binding.btnRequestUpgrade.setOnClickListener(v-> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_contractDetailFragment_to_requestUpgradeFragment);
        });
    }

    private void configureWebViewSettings() {
        WebViewUtils.configureWebViewForPdf(binding.webView, pdfUrl);
    }

    private void loadPdf(String pdfUrl) {
        try {
            WebViewUtils.loadPdf(binding.webView, pdfUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            showError(getString(R.string.error_load_pdf, e.getMessage()));
        }
    }

    private void showError(@NonNull String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorAndFinish(@NonNull String message) {
        showError(message);
    }

    @Override
    public void onDestroyView() {
        if (binding != null && binding.webView != null) {
            binding.webView.stopLoading();
            binding.webView.clearCache(true);
            binding.webView.clearHistory();
            binding.webView.destroy();
        }
        super.onDestroyView();
        binding = null;
    }
}