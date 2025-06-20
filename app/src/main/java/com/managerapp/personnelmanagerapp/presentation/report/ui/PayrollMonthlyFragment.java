package com.managerapp.personnelmanagerapp.presentation.report.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.response.PayrollResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentPayrollMonthlyBinding;
import com.managerapp.personnelmanagerapp.domain.model.Cell;
import com.managerapp.personnelmanagerapp.domain.model.ColumnHeader;
import com.managerapp.personnelmanagerapp.domain.model.Payroll;
import com.managerapp.personnelmanagerapp.domain.model.RowHeader;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import com.managerapp.personnelmanagerapp.presentation.report.adapter.MyTableAdapter;
import com.managerapp.personnelmanagerapp.presentation.report.viewmodel.PayrollViewModel;
import com.managerapp.personnelmanagerapp.utils.CurrencyUtils;
import com.managerapp.personnelmanagerapp.utils.DateUtils;
import com.managerapp.personnelmanagerapp.utils.ExcelUtils;
import com.managerapp.personnelmanagerapp.utils.PdfUtils;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PayrollMonthlyFragment extends Fragment {
    private FragmentPayrollMonthlyBinding binding;
    private PayrollViewModel payrollViewModel;
    private MyTableAdapter adapter;
    private NavController navController;
    private UiState currentState;

    private List<ColumnHeader> createColumnHeaders() {
        List<ColumnHeader> headers = new ArrayList<>();
        headers.add(new ColumnHeader("Mã nhân viên"));
        headers.add(new ColumnHeader("Nhân viên"));
        headers.add(new ColumnHeader("Lương cơ bản"));
        headers.add(new ColumnHeader("Lương thâm niên"));
        headers.add(new ColumnHeader("Số ngày làm việc"));
        headers.add(new ColumnHeader("Số ngày làm thực tế"));
        headers.add(new ColumnHeader("Ngày nghỉ không lương"));
        return headers;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        payrollViewModel = new ViewModelProvider(this).get(PayrollViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPayrollMonthlyBinding.inflate(inflater, container, false);
        adapter = new MyTableAdapter();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController =  Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        onListener();
    }

    public void setUpUi() {

    }

    public void onListener() {
        binding.startDayBtn.setOnClickListener(v -> showDatePickerDialog(true));
        binding.endDateBtn.setOnClickListener(v -> showDatePickerDialog(false));
        binding.generateReportBtn.setOnClickListener(v -> {
            String startDateRaw = binding.textStartDay.getText().toString();
            String endDateRaw = binding.textEndDate.getText().toString();
            String startDate = DateUtils.convertToApiDateFormat(startDateRaw);
            String endDate = DateUtils.convertToApiDateFormat(endDateRaw);
            payrollViewModel.getPayroll(startDate, endDate);
            observe();
        });

        binding.exportExcelBtn.setOnClickListener(v -> {
            if (currentState instanceof UiState.Success) {
                List<Payroll> data = ((UiState.Success<List<Payroll>>) currentState).getData();
                Workbook wb = ExcelUtils.createPayrollWorkbook(data);
                Uri savedUri = ExcelUtils.saveWorkbookToDownloads(requireActivity(), wb, "bang_luong.xlsx");

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
            Toast.makeText(requireContext(), "In báo cáo", Toast.LENGTH_SHORT).show();
            if (currentState instanceof UiState.Success) {
                List<Payroll> data = ((UiState.Success<List<Payroll>>) currentState).getData();
                Log.d("In bao cao", "Đã có dữ liệu để xuất");
                String start = binding.textStartDay.getText().toString();
                String end = binding.textEndDate.getText().toString();
                String dateRange = start + " - " + end;

                PdfUtils.exportPayrollToPdf(requireActivity(), dateRange, data, "Báo cáo lương");
            } else {

                Toast.makeText(requireContext(), "Không có dữ liệu để xuất", Toast.LENGTH_SHORT).show();
            }
        });

        binding.backBtn.setOnClickListener(v-> {
            navController.popBackStack();
        });
    }



    public void observe() {
        payrollViewModel.getPayrollUiState().observe(getViewLifecycleOwner(), state -> {

            if (state instanceof UiState.Loading) {
//                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                currentState = state;
                binding.tableView.setAdapter(adapter);

                adapter.setAllItems(
                        createColumnHeaders(),
                        createRowHeaders(((UiState.Success<List<Payroll>>) state).getData()),
                        createCells(((UiState.Success<List<Payroll>>) state).getData())
                );
            } else if (state instanceof UiState.Error) {
//                binding.progressOverlayWhite.getRoot().setVisibility(View.GONE);
//                PayrollUiState.Error error = (PayrollUiState.Error) state;
//                Toast.makeText(requireContext(), "Lỗi: " + error.message, Toast.LENGTH_LONG).show();

            }
        });
    }


    private List<RowHeader> createRowHeaders(List<Payroll> payrollList) {
        List<RowHeader> headers = new ArrayList<>();
        for (int i = 0; i < payrollList.size(); i++) {
            headers.add(new RowHeader(String.valueOf(i + 1)));
        }
        return headers;
    }

    private List<List<Cell>> createCells(List<Payroll> payrollList) {
        List<List<Cell>> cells = new ArrayList<>();
        for (Payroll p : payrollList) {
            List<Cell> row = new ArrayList<>();
            row.add(new Cell(String.valueOf(p.getUserId())));
            row.add(new Cell(p.getFullName()));
            row.add(new Cell(CurrencyUtils.formatToVNDSimple(p.getSalary())));
            row.add(new Cell(String.valueOf(p.getSeniority())));
            row.add(new Cell(String.valueOf(p.getWorkDays())));
            row.add(new Cell(String.valueOf(CurrencyUtils.doubleToInt(p.getActualWorkDays()))));
            row.add(new Cell(String.valueOf(CurrencyUtils.doubleToInt(p.getUnpaidLeaveDays()))));
            cells.add(row);
        }
        return cells;
    }

    private void showDatePickerDialog(boolean isStartDate) {
        DateUtils.DateComponents currentDate = DateUtils.getCurrentDateComponents();
        Calendar calendar = Calendar.getInstance();
        calendar.set(currentDate.year, currentDate.month - 1, currentDate.day);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = DateUtils.formatDate(selectedDay, selectedMonth + 1, selectedYear);
                    if (isStartDate) {
                        binding.textStartDay.setText(selectedDate);
                    } else {
                        binding.textEndDate.setText(selectedDate);
                    }
                    validateDates();
                },
                currentDate.year, currentDate.month - 1, currentDate.day
        );

        if (!isStartDate) {
            String startDateStr = binding.textStartDay.getText().toString();
            Calendar minDate = parseDateToCalendar(startDateStr);
            if (minDate != null) {
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            }
        }

        if (isStartDate) {
            String endDateStr = binding.textEndDate.getText().toString();
            Calendar maxDate = parseDateToCalendar(endDateStr);
            if (maxDate != null) {
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            }
        }

        datePickerDialog.show();
    }

    private void validateDates() {
        String startDateStr = binding.textStartDay.getText().toString();
        String endDateStr = binding.textEndDate.getText().toString();

        Calendar startDate = parseDateToCalendar(startDateStr);
        Calendar endDate = parseDateToCalendar(endDateStr);

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            binding.textEndDate.setText(startDateStr);
        }
    }

    private Calendar parseDateToCalendar(String dateStr) {
        try {
            String[] parts = dateStr.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int year = Integer.parseInt(parts[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            return calendar;
        } catch (Exception e) {
            return null;
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}