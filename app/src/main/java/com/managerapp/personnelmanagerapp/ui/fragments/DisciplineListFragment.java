package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.databinding.FragmentDisciplineListBinding;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;


public class DisciplineListFragment extends BaseFragment {

    private FragmentDisciplineListBinding binding;
    public DisciplineListFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDisciplineListBinding.inflate(inflater, container, false);
        binding.emptyView.setVisibility(View.VISIBLE);
        return binding.getRoot();
    }
}