package com.managerapp.personnelmanagerapp.ui.fragments;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.ui.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentNewRequestBinding;
import com.managerapp.personnelmanagerapp.ui.state.CreateLeaveState;
import com.managerapp.personnelmanagerapp.ui.state.LeaveTypesState;
import com.managerapp.personnelmanagerapp.ui.viewmodel.CreateLeaveViewModel;
import com.managerapp.personnelmanagerapp.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class NewLeaveAppFragment extends BaseFragment {

    private FragmentNewRequestBinding binding;
    private CreateLeaveViewModel createLeaveViewModel;
    private int leaveTypeId = -1; // Khởi tạo với giá trị không hợp lệ để xác định chưa chọn

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNewRequestBinding.inflate(inflater, container, false);

        createLeaveViewModel = new ViewModelProvider(this).get(CreateLeaveViewModel.class);
        binding.backBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_newRequestFragment_to_requestHistoryFragment);
        });

        loadLeaveTypes();
        setupObservers();
        updateButtonWithCurrentDate();
        setOnListener();

        return binding.getRoot();
    }

    private void setupObservers() {
        // Setup create leave state observer
        createLeaveViewModel.getCreateLeaveState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof CreateLeaveState.Loading) {
                binding.progressOverlay.setVisibility(VISIBLE);
                binding.detailActivity.setClickable(false);
                binding.btnSend.setEnabled(false);
            } else if (state instanceof CreateLeaveState.Success){
                binding.progressOverlay.setVisibility(GONE);
                Toast.makeText(requireContext(), "Gửi đơn thành công", Toast.LENGTH_SHORT).show();
                // Chuyển về màn hình danh sách sau 1.5s
                new Handler().postDelayed(() -> {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.request_fragment);
                    navController.navigate(R.id.action_newRequestFragment_to_requestHistoryFragment);
                }, 1500);
            } else if (state instanceof CreateLeaveState.Error) {
                binding.progressOverlay.setVisibility(GONE);
                binding.btnSend.setEnabled(true);
                binding.detailActivity.setClickable(true);
                Toast.makeText(requireContext(), ((CreateLeaveState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadLeaveTypes() {
        binding.swipeRefresh.setRefreshing(true);
        createLeaveViewModel.getLeaveTypesState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof LeaveTypesState.Loading) {
                binding.swipeRefresh.setRefreshing(true);
                binding.detailActivity.setVisibility(GONE);
            } else if (state instanceof LeaveTypesState.Success) {
                binding.swipeRefresh.setRefreshing(false);
                binding.emptyView.setVisibility(GONE);

                List<LeaveType> leaveTypes = ((LeaveTypesState.Success) state).getLeaveTypes();

                if (leaveTypes == null || leaveTypes.isEmpty()) {
                    binding.emptyView.setVisibility(VISIBLE);
                    binding.detailActivity.setVisibility(GONE);
                    return;
                }

                // Tạo adapter tùy chỉnh chỉ hiển thị tên thay vì đối tượng
                ArrayAdapter<LeaveType> adapter = new ArrayAdapter<LeaveType>(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        leaveTypes
                ) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view;
                        LeaveType leaveType = getItem(position);
                        if (leaveType != null) {
                            textView.setText(leaveType.getName()); // Chỉ hiển thị tên
                        }
                        return view;
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView textView = (TextView) view;
                        LeaveType leaveType = getItem(position);
                        if (leaveType != null) {
                            textView.setText(leaveType.getName()); // Chỉ hiển thị tên trong dropdown
                        }
                        return view;
                    }
                };

                adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
                binding.spinnerReasons.setAdapter(adapter);

                // Gán listener khi chọn xong để lấy id
                binding.spinnerReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        LeaveType selected = (LeaveType) parent.getItemAtPosition(position);
                        leaveTypeId = selected.getId();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        leaveTypeId = -1; // Đặt lại nếu không có gì được chọn
                    }
                });

                binding.detailActivity.setVisibility(VISIBLE);
            } else if (state instanceof LeaveTypesState.Error) {
                binding.swipeRefresh.setRefreshing(false);
                binding.emptyView.setVisibility(VISIBLE);
                binding.detailActivity.setVisibility(GONE);
                Toast.makeText(requireContext(), ((LeaveTypesState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        createLeaveViewModel.getLeaveTypes();
    }

    // Kiểm tra dữ liệu form trước khi gửi
    private boolean validateForm() {
        // Kiểm tra đã chọn loại nghỉ phép chưa
        if (leaveTypeId <= 0) {
            Toast.makeText(requireContext(), "Vui lòng chọn loại nghỉ phép", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra đã nhập lý do
        if (binding.edtReason.getText().toString().trim().isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập lý do nghỉ phép", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Kiểm tra ngày hợp lệ
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date startDateObj = inputFormat.parse(binding.textStartDay.getText().toString());
            Date endDateObj = inputFormat.parse(binding.textEndDate.getText().toString());

            if (startDateObj == null || endDateObj == null) {
                Toast.makeText(requireContext(), "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (ParseException e) {
            Toast.makeText(requireContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Thiết lập sự kiện
    public void setOnListener() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            createLeaveViewModel.getLeaveTypes();
        });

        // Xử lý sự kiện nhấn nút chọn ngày bắt đầu
        binding.startDayBtn.setOnClickListener(v -> {
            showDatePickerDialog(true); // true: chọn ngày bắt đầu
        });

        // Xử lý sự kiện nhấn nút chọn ngày kết thúc
        binding.endDateBtn.setOnClickListener(v -> {
            showDatePickerDialog(false); // false: chọn ngày kết thúc
        });

        binding.btnSend.setOnClickListener(v -> {
            if (!validateForm()) {
                return;
            }

            try {
                // Phân tích chuỗi ngày từ UI
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                // Đối tượng Date từ chuỗi đầu vào
                Date startDateObj = inputFormat.parse(binding.textStartDay.getText().toString());
                Date endDateObj = inputFormat.parse(binding.textEndDate.getText().toString());

                // Chuyển sang định dạng mà API yêu cầu (ISO 8601)
                SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String startDateFormatted = apiFormat.format(startDateObj);
                String endDateFormatted = apiFormat.format(endDateObj);

                // Tạo request với chuỗi ngày đã được định dạng lại
                LeaveApplicationRequest leaveApplicationRequest = new LeaveApplicationRequest(
                        startDateFormatted,  // Gửi chuỗi thay vì Date object
                        endDateFormatted,    // Gửi chuỗi thay vì Date object
                        binding.edtReason.getText().toString(),
                        leaveTypeId);

                // Gửi request
                createLeaveViewModel.sendLeaveApplication(leaveApplicationRequest);
            } catch (ParseException e) {
                Toast.makeText(requireContext(), "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove observers để tránh memory leak
        createLeaveViewModel.getCreateLeaveState().removeObservers(getViewLifecycleOwner());
        createLeaveViewModel.getLeaveTypesState().removeObservers(getViewLifecycleOwner());
        binding = null;
    }
}