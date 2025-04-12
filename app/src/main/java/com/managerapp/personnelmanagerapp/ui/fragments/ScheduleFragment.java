package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentScheduleBinding;
import com.managerapp.personnelmanagerapp.ui.adapters.DateAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleFragment extends BaseFragment {

    private FragmentScheduleBinding binding;

    public ScheduleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout với View Binding
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng bộ nhớ tránh memory leak
    }

}
