package com.managerapp.personnelmanagerapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentProfileBinding;
import com.managerapp.personnelmanagerapp.ui.activities.ProfileInfoActivity;
import com.managerapp.personnelmanagerapp.ui.activities.SettingsActivity;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());


        binding.btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });
        binding.btnProfileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ProfileInfoActivity.class);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }
}