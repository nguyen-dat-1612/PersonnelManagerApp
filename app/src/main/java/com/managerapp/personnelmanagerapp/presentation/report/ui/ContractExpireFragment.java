package com.managerapp.personnelmanagerapp.presentation.report.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.ContractExpireReportResponse;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentContractExpireBinding;
import com.managerapp.personnelmanagerapp.domain.model.Cell;
import com.managerapp.personnelmanagerapp.domain.model.ColumnHeader;
import com.managerapp.personnelmanagerapp.domain.model.ContractExpireReport;
import com.managerapp.personnelmanagerapp.domain.model.RowHeader;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.report.adapter.MyTableAdapter;
import com.managerapp.personnelmanagerapp.presentation.report.viewmodel.ContractExpireViewModel;
import com.managerapp.personnelmanagerapp.utils.DateUtils;
import com.managerapp.personnelmanagerapp.utils.ExcelUtils;
import com.managerapp.personnelmanagerapp.utils.PdfUtils;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContractExpireFragment extends Fragment {
    private FragmentContractExpireBinding binding;
    private ContractExpireViewModel viewModel;
    private MyTableAdapter adapter;
    private UiState currentState;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ContractExpireViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContractExpireBinding.inflate(inflater, container, false);
        adapter = new MyTableAdapter();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        onListener();
        observeData();
        NumberPicker numberPicker = binding.numberPickerDays;
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(90);
        numberPicker.setWrapSelectorWheel(true);
    }

    private void onListener() {
        binding.backBtn.setOnClickListener(v -> {
            navController.popBackStack();
        });

        binding.btnExport.setOnClickListener(v -> {
            int days = binding.numberPickerDays.getValue();
            viewModel.loadContractsFilteredBy(days);
        });

        binding.exportExcelBtn.setOnClickListener(v -> {
            int days = binding.numberPickerDays.getValue();
            viewModel.exportExcel(requireContext(), days);

            if (currentState instanceof UiState.Success) {
                List<ContractExpireReport> data = ((UiState.Success<List<ContractExpireReport>>) currentState).getData();
                Workbook wb = ExcelUtils.createContractExpireWorkbook(data);
                Uri savedUri = ExcelUtils.saveWorkbookToDownloads(requireActivity(), wb, "bang_hop_dong_het_han.xlsx");

                if (savedUri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(savedUri, "application/pdf");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    requireContext().startActivity(intent);
                } else {
                    // Xử lý lỗi lưu file
                }
            } else {
                Toast.makeText(requireContext(), "Không có dữ liệu để xuất", Toast.LENGTH_SHORT).show();
            }
        });

        binding.printReportBtn.setOnClickListener(v -> {
            int days = binding.numberPickerDays.getValue();
            viewModel.exportPdfReport(requireContext(), days);
            if (currentState instanceof UiState.Success) {
                List<ContractExpireReport> data = ((UiState.Success<List<ContractExpireReport>>) currentState).getData();
                String currentDate = DateUtils.getCurrentDateAsString();

                PdfUtils.exportContractExpireReport(requireActivity(), currentDate,days, data, "Báo cáo hợp đồng sắp hết hạn");
            } else {

                Toast.makeText(requireContext(), "Không có dữ liệu để xuất", Toast.LENGTH_SHORT).show();
            }
        });
        binding.numberPickerDays.setOnValueChangedListener((picker, oldVal, newVal) -> {
            viewModel.setSelectedDays(newVal);
        });
    }

    private void observeData() {
        viewModel.getContractExpireUiState().observe(getViewLifecycleOwner(), uiState -> {
            if (uiState instanceof UiState.Loading) {
                binding.loadingOverlay.setVisibility(View.VISIBLE);
            } else {
                binding.loadingOverlay.setVisibility(View.GONE);
            }

            if (uiState instanceof UiState.Success) {
                currentState = uiState;
                binding.tableView.setAdapter(adapter);
                adapter.setAllItems(
                        createColumnHeaders(),
                        createRowHeaders(((UiState.Success<List<ContractExpireReport>>) uiState).getData()),
                        createCells(((UiState.Success<List<ContractExpireReport>>) uiState).getData())
                );
            }

            if (uiState instanceof UiState.Error) {
                String errorMsg = ((UiState.Error) uiState).getErrorMessage();
                Toast.makeText(requireContext(), "Lỗi: " + errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getSelectedDays().observe(getViewLifecycleOwner(), days -> {
            binding.numberPickerDays.setValue(days);
            viewModel.loadContractsFilteredBy(days);
        });
    }

    private List<ColumnHeader> createColumnHeaders() {
        List<ColumnHeader> headers = new ArrayList<>();
        headers.add(new ColumnHeader("Họ tên"));
        headers.add(new ColumnHeader("Email"));
        headers.add(new ColumnHeader("Phòng ban"));
        headers.add(new ColumnHeader("Chức vụ"));
        headers.add(new ColumnHeader("Loại HĐ"));
        headers.add(new ColumnHeader("Ngày hết hạn"));
        headers.add(new ColumnHeader("Còn lại (ngày)"));
        headers.add(new ColumnHeader("Trạng thái"));
        return headers;
    }
    private List<RowHeader> createRowHeaders(List<ContractExpireReport> contractExpire) {
        List<RowHeader> headers = new ArrayList<>();
        for (int i = 0; i < contractExpire.size(); i++) {
            headers.add(new RowHeader(String.valueOf(i + 1)));
        }
        return headers;
    }

    private List<List<Cell>> createCells(List<ContractExpireReport> contractExpire) {
        List<List<Cell>> cells = new ArrayList<>();
        for (ContractExpireReport c : contractExpire) {
            List<Cell> row = new ArrayList<>();
            row.add(new Cell(c.getFullName()));
            row.add(new Cell(String.valueOf(c.getEmail())));
            row.add(new Cell(String.valueOf(c.getDepartmentName())));
            row.add(new Cell(String.valueOf(c.getPositionName())));
            row.add(new Cell(String.valueOf(c.getContractTypeName())));
            row.add(new Cell(String.valueOf(DateUtils.convertToDayMonthYearFormat(c.getEndDate()))));
            row.add(new Cell(String.valueOf(c.getRemainingDays())));
            row.add(new Cell(String.valueOf(c.getContractStatus())));
            cells.add(row);
        }
        return cells;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
