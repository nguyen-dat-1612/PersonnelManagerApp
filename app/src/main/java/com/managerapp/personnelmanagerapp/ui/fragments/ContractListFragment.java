package com.managerapp.personnelmanagerapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractDetailBinding;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractListBinding;
import com.managerapp.personnelmanagerapp.domain.model.Contract;
import com.managerapp.personnelmanagerapp.ui.activities.MainActivity;
import com.managerapp.personnelmanagerapp.ui.adapters.ContractAdapter;

import java.util.ArrayList;
import java.util.List;


public class ContractListFragment extends Fragment {

    private static final String TAG = "ContractListFragment";
    private FragmentContractListBinding binding;
    private NavController navController;

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

        view.post(() -> {
            try {
                navController = Navigation.findNavController(view);
            } catch (IllegalStateException e) {
                Log.e(TAG, "NavController chưa sẵn sàng", e);
            }
        });

        List<Contract> contracts = new ArrayList<>();

        contracts.add(new Contract(
                "CT001", "EMP001", "CTT",
                "2023-01-01", "2023-12-31",
                10000000, 2000000, 1000000,
                3, true));

        contracts.add(new Contract(
                "CT002", "EMP002", "CTV",
                "2023-05-01", "2023-11-30",
                8000000, 1500000, 500000,
                2, true));

        contracts.add(new Contract(
                "CT003", "EMP003", "CTT",
                "2022-07-01", "2023-06-30",
                12000000, 2500000, 2000000,
                4, false));

        contracts.add(new Contract(
                "CT004", "EMP004", "CTV",
                "2023-02-01", "2023-08-31",
                7500000, 1200000, 600000,
                2, true));

        contracts.add(new Contract(
                "CT005", "EMP005", "CTT",
                "2021-09-01", "2023-09-01",
                14000000, 3000000, 2500000,
                5, false));

        if (contracts.isEmpty()) {
            Toast.makeText(requireContext(), "Không có hợp đồng nào!", Toast.LENGTH_SHORT).show();
        }

        binding.recyclerViewContact.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewContact.setAdapter(new ContractAdapter(contracts,requireContext(), contract -> {
            Bundle bundle = new Bundle();
            bundle.putString("contractId",contract.getContractId());
            navController.navigate(R.id.action_contractListFragment_to_contractDetailFragment, bundle);
        }));

        binding.backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        return view;
    }
}