package com.managerapp.personnelmanagerapp.presentation.contract.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.DialogSalaryPromotionDetailHistoryBinding;
import com.managerapp.personnelmanagerapp.databinding.FragmentHistoryApproveBinding;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.presentation.contract.adapter.SalaryPromotionAdapter;
import com.managerapp.personnelmanagerapp.presentation.contract.viewmodel.HistoryApproveViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryApproveFragment extends Fragment {

    private FragmentHistoryApproveBinding binding;
    private HistoryApproveViewModel viewModel;
    private SalaryPromotionAdapter adapter;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HistoryApproveViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryApproveBinding.inflate(inflater, container, false);
        setUpUI();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onListener();
        observeView();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
    }

    public void setUpUI() {
        adapter = new SalaryPromotionAdapter(promotion -> {
            showPromotionDetailDialog(requireContext(), promotion);
        });
        binding.recyclerViewSalary.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewSalary.setAdapter(adapter);

        List<FormStatusEnum> filteredStatusList = Arrays.stream(FormStatusEnum.values())
                .filter(status -> status != FormStatusEnum.PENDING)  // Loại bỏ PENDING
                .collect(Collectors.toList());

        ArrayAdapter<FormStatusEnum> statusAdapter = new ArrayAdapter<FormStatusEnum>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                filteredStatusList
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setText(getContext().getString(getItem(position).getLocalizedStringRes()));
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setText(getContext().getString(getItem(position).getLocalizedStringRes()));
                return view;
            }
        };

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerStatus.setAdapter(statusAdapter);
    }

    public void onListener() {
        binding.backBtn.setOnClickListener(v -> navController.popBackStack());

        binding.spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                if (FormStatusEnum.APPROVED.toString().equals(selectedStatus)) {
                    viewModel.getSalaryPromotions(FormStatusEnum.APPROVED);
                } else if (FormStatusEnum.REJECTED.toString().equals(selectedStatus)) {
                    viewModel.getSalaryPromotions(FormStatusEnum.REJECTED);
                } else {
                    Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showPromotionDetailDialog(Context context, SalaryPromotion model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);

        DialogSalaryPromotionDetailHistoryBinding binding = DialogSalaryPromotionDetailHistoryBinding.inflate(LayoutInflater.from(context));
        builder.setView(binding.getRoot());

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        binding.tvUser.setText(getString(R.string.label_user, model.getUserName()));
        binding.tvCurrentGrade.setText(getString(R.string.label_current_grade, model.getCurrentJobGradeName()));
        binding.tvRequestGrade.setText(getString(
                R.string.label_request_grade,
                model.getRequestJobGradeName(),
                (int) model.getRequestJobGradeValue()
        ));
        binding.tvReason.setText(getString(R.string.label_reason_grade, model.getReason()));
        binding.tvNote.setText(getString(R.string.dialog_note,
                TextUtils.isEmpty(model.getNote()) ? getString(R.string.dialog_note_empty) : model.getNote()));

        if ("APPROVED".equals(model.getStatus())) {
            binding.tvStatus.setText(getString(R.string.dialog_status_approved));
            binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else if ("REJECTED".equals(model.getStatus())) {
            binding.tvStatus.setText(getString(R.string.dialog_status_rejected));
            binding.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        dialog.show();
    }

    public void observeView() {
        viewModel.getSalaryPromotionsUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                List<SalaryPromotion> data = ((UiState.Success<List<SalaryPromotion>>) state).getData();
                if (data != null && !data.isEmpty()) {
                    adapter.submitList(data);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        binding.progressOverlay.getRoot().setVisibility(View.GONE);
                    }, 500);
                }
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.errorView.getRoot().setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}