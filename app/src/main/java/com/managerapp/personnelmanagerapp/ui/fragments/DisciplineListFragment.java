package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.databinding.FragmentDisciplineListBinding;
import com.managerapp.personnelmanagerapp.databinding.FragmentRewardListBinding;
import com.managerapp.personnelmanagerapp.ui.adapters.DisciplineAdapter;
import com.managerapp.personnelmanagerapp.ui.adapters.RewardAdapter;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.ui.state.DisciplineState;
import com.managerapp.personnelmanagerapp.ui.state.RewardState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.DisciplineViewModel;
import com.managerapp.personnelmanagerapp.ui.viewmodel.RewardViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DisciplineListFragment extends BaseFragment {

    private FragmentDisciplineListBinding binding;
    private final String TAG = "DisciplineListFragment";
    private long userId;
    private DisciplineViewModel disciplineViewModel;
    private DisciplineAdapter adapter;
    public DisciplineListFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDisciplineListBinding.inflate(inflater, container, false);
        userId = requireActivity().getIntent().getLongExtra("userId", -1);
        if (userId <= 0) {
            Log.e(TAG, "Không nhận được userId từ intent");
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disciplineViewModel = new ViewModelProvider(this).get(DisciplineViewModel.class);

        loadDiscipline();

        // Pull to refresh
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            loadDiscipline();
        });
    }

    public void loadDiscipline() {
        disciplineViewModel.getDisciplineState().observe(getViewLifecycleOwner(), disciplineState -> {
            if (disciplineState instanceof DisciplineState.Loading) {
                binding.swipeRefreshLayout.setRefreshing(true);
            } else if(disciplineState instanceof  DisciplineState.ListSuccess) {
                binding.recyclerViewDisciplines.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new DisciplineAdapter(((DisciplineState.ListSuccess) disciplineState).getData());
                binding.recyclerViewDisciplines.setAdapter(adapter);
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.recyclerViewDisciplines.setVisibility(View.VISIBLE);
                binding.emptyView.setVisibility(View.GONE);
            } else if(disciplineState instanceof  DisciplineState.Error) {
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.recyclerViewDisciplines.setVisibility(View.GONE);
                binding.emptyView.setVisibility(View.VISIBLE);
            }
        });
        disciplineViewModel.loadAllRewards((int) userId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Tránh memory leak
    }
}