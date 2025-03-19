package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractDetailBinding;


public class ContractDetailFragment extends Fragment {

    private static final String TAG = "ContractDetailFragment";
    private FragmentContractDetailBinding binding;
    private String contractId;

    public ContractDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContractDetailBinding.inflate(inflater, container, false);


        // Lấy contractId từ arguments
        Bundle args = getArguments();
        contractId = args != null ? args.getString("contractId", "DEFAULT_ID") : "DEFAULT_ID";


        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            if (!navController.popBackStack()) {
                requireActivity().onBackPressed();
            }
        });

        // Debug: Kiểm tra contractId
        Log.d("ContractDetailFragment", "Received contractId: " + contractId);

        return binding.getRoot();
    }
}