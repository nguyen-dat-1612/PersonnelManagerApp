package com.managerapp.personnelmanagerapp.ui.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentNewRequestBinding;
import com.managerapp.personnelmanagerapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NewRequestFragment extends BaseFragment {

    private FragmentNewRequestBinding binding;


    public NewRequestFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewRequestBinding.inflate(inflater, container, false);

        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_newRequestFragment_to_requestHistoryFragment);
        });

        List<String> items = new ArrayList<>();
        items.add("Nghỉ ốm");
        items.add("Nghỉ thai sản");
        items.add("Nghỉ công tác");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                items
        );
        // Tùy chỉnh giao diện danh sách thả xuống
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        binding.spinnerReasons.setAdapter(adapter);

        // Cập nhật ngày hiện tại cho cả hai button
        updateButtonWithCurrentDate();

        // Xử lý sự kiện nhấn nút chọn ngày bắt đầu
        binding.startDayBtn.setOnClickListener(v -> {
            showDatePickerDialog(true); // true: chọn ngày bắt đầu
        });

        // Xử lý sự kiện nhấn nút chọn ngày kết thúc
        binding.endDateBtn.setOnClickListener(v -> {
            showDatePickerDialog(false); // false: chọn ngày kết thúc
        });

        return binding.getRoot();
    }





    // Cập nhật ngày hiện tại ban đầu cho cả hai TextView
    private void updateButtonWithCurrentDate() {
        DateUtils.DateComponents date = DateUtils.getCurrentDateComponents();
        String currentDate = DateUtils.formatDate(date.day, date.month, date.year);
        binding.textStartDay.setText(currentDate);
        binding.textEndDate.setText(currentDate);
    }

    // Hiển thị DatePickerDialog với ràng buộc
    private void showDatePickerDialog(boolean isStartDate) {
        DateUtils.DateComponents currentDate = DateUtils.getCurrentDateComponents();
        Calendar calendar = Calendar.getInstance();
        calendar.set(currentDate.year, currentDate.month - 1, currentDate.day); // Trừ 1 vì tháng trong Calendar bắt đầu từ 0

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = DateUtils.formatDate(selectedDay, selectedMonth + 1, selectedYear);
                    if (isStartDate) {
                        binding.textStartDay.setText(selectedDate);
                        validateDates(); // Kiểm tra ràng buộc
                    } else {
                        binding.textEndDate.setText(selectedDate);
                        validateDates(); // Kiểm tra ràng buộc
                    }
                },
                currentDate.year, currentDate.month - 1, currentDate.day
        );

        // Ràng buộc: Không cho phép chọn ngày trong quá khứ
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Ràng buộc thêm cho ngày kết thúc
        if (!isStartDate) {
            String startDateStr = binding.textStartDay.getText().toString();
            Calendar minDate = parseDateToCalendar(startDateStr);
            if (minDate != null) {
                datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
            }
        }

        // Ràng buộc thêm cho ngày bắt đầu (không sau ngày kết thúc)
        if (isStartDate) {
            String endDateStr = binding.textEndDate.getText().toString();
            Calendar maxDate = parseDateToCalendar(endDateStr);
            if (maxDate != null) {
                datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
            }
        }

        datePickerDialog.show();
    }

    // Chuyển đổi chuỗi ngày thành Calendar để đặt ràng buộc
    private Calendar parseDateToCalendar(String dateStr) {
        try {
            String[] parts = dateStr.split("/");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // Trừ 1 vì tháng bắt đầu từ 0
            int year = Integer.parseInt(parts[2]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            return calendar;
        } catch (Exception e) {
            return null; // Nếu lỗi, không đặt ràng buộc
        }
    }

    // Kiểm tra và điều chỉnh nếu ngày bắt đầu lớn hơn ngày kết thúc
    private void validateDates() {
        String startDateStr = binding.textStartDay.getText().toString();
        String endDateStr = binding.textEndDate.getText().toString();

        Calendar startDate = parseDateToCalendar(startDateStr);
        Calendar endDate = parseDateToCalendar(endDateStr);

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            // Nếu ngày bắt đầu lớn hơn ngày kết thúc, điều chỉnh ngày kết thúc bằng ngày bắt đầu
            binding.textEndDate.setText(startDateStr);
        }
    }
}