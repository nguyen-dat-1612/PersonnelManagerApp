package com.managerapp.personnelmanagerapp.presentation.contract.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentSalaryPromotionApproveBinding;
import com.managerapp.personnelmanagerapp.domain.model.FormStatusEnum;
import com.managerapp.personnelmanagerapp.domain.model.SalaryPromotion;
import com.managerapp.personnelmanagerapp.presentation.contract.adapter.SalaryPromotionAdapter;
import com.managerapp.personnelmanagerapp.presentation.contract.viewmodel.ApproveSalaryViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SalaryPromotionApproveFragment extends Fragment {
    private FragmentSalaryPromotionApproveBinding binding;
    private ApproveSalaryViewModel viewModel;
    private SalaryPromotionAdapter adapter;
    private NavController navController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ApproveSalaryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSalaryPromotionApproveBinding.inflate(inflater, container, false);
        setUpUI();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeView();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        onListener();
    }

    public void setUpUI() {
        adapter = new SalaryPromotionAdapter(promotion -> {
            showPromotionDetailDialog(requireContext(), promotion); // Viết dialog như phần trước
        });
        binding.recyclerViewSalary.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewSalary.setAdapter(adapter);

    }

    public void onListener() {
        binding.backBtn.setOnClickListener(v -> navController.popBackStack());

        binding.btnHistory.setOnClickListener(v -> {
            navController.navigate(R.id.action_salaryPromotionApproveFragment_to_historyApproveFragment);
        });
    }

    private void showPromotionDetailDialog(Context context, SalaryPromotion promotion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog); // nếu muốn style bo góc
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_salary_promotion_detail, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true); // Không tắt khi bấm ra ngoài

        // Bind view
        TextView tvUser = view.findViewById(R.id.tvUser);
        TextView tvCurrent = view.findViewById(R.id.tvCurrentGrade);
        TextView tvRequest = view.findViewById(R.id.tvRequestGrade);
        TextView tvReason = view.findViewById(R.id.tvReason);
        EditText edtNote = view.findViewById(R.id.edtNote);
        Button btnApprove = view.findViewById(R.id.btnApprove);
        Button btnReject = view.findViewById(R.id.btnReject);

        // Set data
        tvUser.setText("👤 Người đề xuất: " + promotion.getUserName());
        tvCurrent.setText("🏷️ Bậc hiện tại: " + promotion.getCurrentJobGradeName());
        tvRequest.setText("📈 Bậc đề xuất: " + promotion.getRequestJobGradeName() + " (" + promotion.getRequestJobGradeValue() + ")");
        tvReason.setText("📝 Lý do: " + promotion.getReason());

        // Focus ghi chú
        edtNote.requestFocus();

        // Handle nút
        btnApprove.setOnClickListener(v -> {
            String note = edtNote.getText().toString().trim();
            if (note.isEmpty()) {
                edtNote.setError("Vui lòng nhập ghi chú trước khi phê duyệt");
                return;
            }
            viewModel.approveSalaryPromotion((int) promotion.getId(), FormStatusEnum.APPROVED, note);
            dialog.dismiss();
        });

        btnReject.setOnClickListener(v -> {
            String note = edtNote.getText().toString().trim();
            if (note.isEmpty()) {
                edtNote.setError("Vui lòng nhập ghi chú trước khi từ chối");
                return;
            }
            viewModel.approveSalaryPromotion((int) promotion.getId(), FormStatusEnum.REJECTED, note);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void observeView() {
        viewModel.getSalaryPromotionUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                List<SalaryPromotion> promotions = ((UiState.Success<List<SalaryPromotion>>) state).getData();
                if (promotions.isEmpty()) {
                    binding.emptyView.getRoot().setVisibility(View.VISIBLE);
                } else {
                    binding.emptyView.getRoot().setVisibility(View.GONE);
                }
                adapter.submitList(promotions);
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.errorView.getRoot().setVisibility(View.VISIBLE);
            }
        });

        viewModel.getUpdateSalaryPromotionUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                SalaryPromotion promotion = ((UiState.Success<SalaryPromotion>) state).getData();
                if (promotion.getStatus().equals(FormStatusEnum.APPROVED.toString())) {
                    Toast.makeText(requireContext(), "Phê duyệt thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Từ chối thành công", Toast.LENGTH_SHORT).show();
                }
            } else if (state instanceof UiState.Error) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
            }
        });
    }

}