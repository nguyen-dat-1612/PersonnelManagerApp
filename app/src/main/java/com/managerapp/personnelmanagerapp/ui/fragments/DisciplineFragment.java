package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentDisciplineBinding;


public class DisciplineFragment extends Fragment {

    private FragmentDisciplineBinding binding;
    public DisciplineFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDisciplineBinding.inflate(inflater, container, false);
        binding.emptyView.setVisibility(View.VISIBLE);
        return binding.getRoot();
    }
}