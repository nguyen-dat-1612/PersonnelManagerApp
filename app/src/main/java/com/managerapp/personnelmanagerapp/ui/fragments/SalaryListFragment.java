package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentSalaryListBinding;
import com.managerapp.personnelmanagerapp.domain.model.Salary;
import com.managerapp.personnelmanagerapp.ui.adapters.SalaryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SalaryListFragment extends Fragment {
    private static final String TAG = "SalaryListFragment";
    private FragmentSalaryListBinding binding;
    private NavController navController;
    public SalaryListFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSalaryListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        view.post(() -> {
            try {
                navController = Navigation.findNavController(view);
            } catch (IllegalStateException e) {
                Log.e(TAG, "NavController chưa sẵn sàng", e);
            }
        });
        List<Salary> salaryList = new ArrayList<>();
//
//        salaryList.add(new Salary("1", "E001", "C001", "2024-01", "500", "50", "100", "150", "2024-02-01", "Paid"));
//        salaryList.add(new Salary("2", "E002", "C002", "2024-01", "600", "40", "120", "160", "2024-02-02", "Pending"));
//        salaryList.add(new Salary("3", "E003", "C003", "2024-01", "550", "30", "110", "140", "2024-02-03", "Paid"));
//        salaryList.add(new Salary("4", "E004", "C004", "2024-01", "700", "20", "140", "160", "2024-02-04", "Paid"));
//        salaryList.add(new Salary("5", "E005", "C005", "2024-01", "450", "25", "90", "115", "2024-02-05", "Pending"));
//        salaryList.add(new Salary("6", "E006", "C006", "2024-01", "800", "10", "160", "170", "2024-02-06", "Paid"));
//        salaryList.add(new Salary("7", "E007", "C007", "2024-01", "750", "15", "150", "165", "2024-02-07", "Paid"));
//        salaryList.add(new Salary("8", "E008", "C008", "2024-01", "680", "35", "130", "165", "2024-02-08", "Pending"));
//        salaryList.add(new Salary("9", "E009", "C009", "2024-01", "620", "45", "125", "170", "2024-02-09", "Paid"));
//        salaryList.add(new Salary("10", "E010", "C010", "2024-01", "580", "20", "115", "135", "2024-02-10", "Pending"));


        binding.recyclerViewSalary.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewSalary.setAdapter(new SalaryAdapter(salaryList,id -> {
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            navController.navigate(R.id.action_salaryListFragment_to_salaryDetailFragment, bundle);
        }));
        return view;
    }
}