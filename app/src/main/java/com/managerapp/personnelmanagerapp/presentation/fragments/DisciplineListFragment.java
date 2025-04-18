package com.managerapp.personnelmanagerapp.presentation.fragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.managerapp.personnelmanagerapp.data.remote.response.AssignmentResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentDisciplineListBinding;
import com.managerapp.personnelmanagerapp.presentation.adapters.DisciplineAdapter;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.viewmodel.DisciplineViewModel;

import java.util.List;

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
        disciplineViewModel.getUiState().observe(getViewLifecycleOwner(), disciplineState -> {
            if (disciplineState instanceof UiState.Loading) {
                binding.swipeRefreshLayout.setRefreshing(true);
            } else if(disciplineState instanceof UiState.Success) {
                binding.recyclerViewDisciplines.setLayoutManager(new LinearLayoutManager(requireContext()));
                adapter = new DisciplineAdapter(((UiState.Success<List<AssignmentResponse>>) disciplineState).getData());
                binding.recyclerViewDisciplines.setAdapter(adapter);
                binding.swipeRefreshLayout.setRefreshing(false);
                binding.recyclerViewDisciplines.setVisibility(View.VISIBLE);
                binding.emptyView.setVisibility(View.GONE);
            } else if(disciplineState instanceof  UiState.Error) {
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