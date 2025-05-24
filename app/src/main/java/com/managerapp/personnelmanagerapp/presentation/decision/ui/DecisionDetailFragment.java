package com.managerapp.personnelmanagerapp.presentation.decision.ui;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.DecisionResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentDecisionDetailBinding;
import com.managerapp.personnelmanagerapp.presentation.decision.viewmodel.DecisionDetailViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DecisionDetailFragment extends Fragment {
    private FragmentDecisionDetailBinding binding;
    private DecisionDetailViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lấy decisionId từ Navigation Component arguments
        DecisionDetailFragmentArgs args = DecisionDetailFragmentArgs.fromBundle(getArguments());
        String decisionId = args.getDecisionId();

        // Truyền decisionId qua SavedStateHandle
        Bundle bundle = new Bundle();
        bundle.putString("decision_id", decisionId);
        setArguments(bundle);

        // Hilt sẽ tự động inject ViewModel với SavedStateHandle chứa decisionId
        viewModel = new ViewModelProvider(this).get(DecisionDetailViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDecisionDetailBinding.inflate(inflater, container, false);
        loadData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onListener();
    }

    public void onListener() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refresh();
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_decisionDetailFragment_to_decisionListFragment);
            navController.popBackStack();
        });
    }
    public void loadData() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            binding.progressOverlay.getRoot().setVisibility(
                    state.isLoading() ? VISIBLE : INVISIBLE
            );

            binding.errorView.getRoot().setVisibility(
                    state.getErrorMessage() != null ? VISIBLE : INVISIBLE
            );

            binding.setDecision(
                    state.getDecision() != null ? state.getDecision() : new DecisionResponse()
            );
            if (state.getDecision() != null)  {
                if (state.getDecision().getSalaryPromotion() == null) {
                    binding.salaryPromotionLayout.setVisibility(GONE);
                }
                if (state.getDecision().getPosition() == null) {
                    binding.positionCard.setVisibility(GONE);
                }
                if (state.getDecision().getAttachment() != null) {
                    binding.attachmentCard.setVisibility(GONE);
                }
            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}