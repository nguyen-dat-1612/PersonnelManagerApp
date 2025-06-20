package com.managerapp.personnelmanagerapp.presentation.leaveapp.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.data.remote.request.LeaveApplicationRequest;
import com.managerapp.personnelmanagerapp.databinding.FragmentNewRequestBinding;
import com.managerapp.personnelmanagerapp.domain.model.LeaveType;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.state.CreateLeaveUiState;
import com.managerapp.personnelmanagerapp.presentation.leaveapp.viewmodel.CreateLeaveViewModel;
import com.managerapp.personnelmanagerapp.utils.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private String htmlTemplate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLeaveViewModel = new ViewModelProvider(this).get(CreateLeaveViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewRequestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setupObservers();
        setupViews();
    }

    private void setupViews() {
        binding.backBtn.setOnClickListener(v -> navigateBack());

        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.setInitialScale(100);

        new LoadHtmlTemplateTask().execute();
        updateButtonWithCurrentDate();

        binding.swipeRefresh.setOnRefreshListener(() -> {
            createLeaveViewModel.refreshData();
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.startDayBtn.setOnClickListener(v -> {
            showDatePickerDialog(true);
        });
        binding.endDateBtn.setOnClickListener(v -> {
            showDatePickerDialog(false);
        });

        binding.btnSend.setOnClickListener(v -> submitLeaveApplication());

        setupTextWatchers();
    }

    private void setupObservers() {
        createLeaveViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {

            if (state instanceof CreateLeaveUiState.Loading) {
                binding.progressOverlay.getRoot().setVisibility(View.VISIBLE);
            }

            else if (state instanceof CreateLeaveUiState.DataLoaded) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.viewNoInternet.getRoot().setVisibility(View.GONE);

                CreateLeaveUiState.DataLoaded data = (CreateLeaveUiState.DataLoaded) state;

                binding.nameText.setText(data.userProfile.getFullName());
                binding.positionNameText.setText(data.userProfile.getPositionName());
                binding.departmentNameText.setText(data.userProfile.getDepartmentName());
                createLeaveViewModel.setUserId(data.userProfile.getId());
                setupLeaveTypeSpinner(data.leaveTypes);
            }

            else if (state instanceof CreateLeaveUiState.Error) {
                binding.progressOverlayWhite.getRoot().setVisibility(View.GONE);

                CreateLeaveUiState.Error error = (CreateLeaveUiState.Error) state;
                showToast(error.message);

                if (error.cachedUserProfile != null && error.cachedLeaveTypes != null) {
                    binding.nameText.setText(error.cachedUserProfile.getFullName());
                    binding.positionNameText.setText(error.cachedUserProfile.getPositionName());
                    binding.departmentNameText.setText(error.cachedUserProfile.getDepartmentName());
                    setupLeaveTypeSpinner(error.cachedLeaveTypes);
                }
            }

            else if (state instanceof CreateLeaveUiState.LeaveCreated) {
                binding.progressOverlay.getRoot().setVisibility(View.GONE);
                binding.viewNoInternet.getRoot().setVisibility(View.GONE);

                handleLeaveCreatedSuccess();
            }

        });
    }

    private void setupLeaveTypeSpinner(List<LeaveType> leaveTypes) {
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
                    textView.setText(leaveType.getName());
                }
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                LeaveType leaveType = getItem(position);
                if (leaveType != null) {
                    textView.setText(leaveType.getName());
                }
                return view;
            }
        };

        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        binding.spinnerReasons.setAdapter(adapter);

        binding.spinnerReasons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeaveType selected = (LeaveType) parent.getItemAtPosition(position);
                createLeaveViewModel.setLeaveTypeId(selected.getId());
                updateWebView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                createLeaveViewModel.setLeaveTypeId(0);
                updateWebView();
            }
        });
    }

    private void handleLeaveCreatedSuccess() {
        binding.progressOverlay.getRoot().setVisibility(GONE);
        binding.viewSendSuccess.getRoot().setVisibility(VISIBLE);

        new Handler().postDelayed(() -> {
            navigateBack();
        }, 3000);
    }

    private void submitLeaveApplication() {
        if (!validateForm()) {
            return;
        }
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date startDateObj = inputFormat.parse(binding.textStartDay.getText().toString());
            Date endDateObj = inputFormat.parse(binding.textEndDate.getText().toString());

            SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String startDateFormatted = apiFormat.format(startDateObj);
            String endDateFormatted = apiFormat.format(endDateObj);

            LeaveApplicationRequest request = new LeaveApplicationRequest(
                    startDateFormatted,
                    endDateFormatted,
                    createLeaveViewModel.getUserId(),
                    binding.edtReason.getText().toString(),
                    createLeaveViewModel.getLeaveTypeId());

            createLeaveViewModel.sendLeaveApplication(request);
        } catch (ParseException e) {
            showToast(getString(R.string.msg_invalid_date));
        }
    }

    private boolean validateForm() {
        if (createLeaveViewModel.getLeaveTypeId() <= 0) {
            showToast(getString(R.string.msg_please_select_leave_type));
            return false;
        }

        if (binding.edtReason.getText().toString().trim().isEmpty()) {
            showToast(getString(R.string.msg_please_enter_reason));
            return false;
        }

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date startDateObj = inputFormat.parse(binding.textStartDay.getText().toString());
            Date endDateObj = inputFormat.parse(binding.textEndDate.getText().toString());

            if (startDateObj == null || endDateObj == null) {
                showToast(getString(R.string.msg_invalid_date));
                return false;
            }
        } catch (ParseException e) {
            showToast(getString(R.string.msg_invalid_date_format));
            return false;
        }

        return true;
    }

    private void setupTextWatchers() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateWebView();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        binding.textStartDay.addTextChangedListener(watcher);
        binding.textEndDate.addTextChangedListener(watcher);
        binding.edtReason.addTextChangedListener(watcher);
    }

    private void updateWebView() {
        if (htmlTemplate == null) {
            Log.e("NewLeaveAppFragment", "HTML template is null");
            return;
        }


        String filledHtml = htmlTemplate
                .replace("{{name}}", binding.nameText.getText().toString())
                .replace("{{position}}", binding.positionNameText.getText().toString())
                .replace("{{department}}", binding.departmentNameText.getText().toString())
                .replace("{{reason}}", binding.edtReason.getText().toString())
                .replace("{{from}}", binding.textStartDay.getText().toString())
                .replace("{{to}}", binding.textEndDate.getText().toString())
                .replace("{{day}}", String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))
                .replace("{{month}}", String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1))
                .replace("{{year}}", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));

        binding.webView.loadDataWithBaseURL(null, filledHtml, "text/html", "UTF-8", null);
    }

    private void updateButtonWithCurrentDate() {
        DateUtils.DateComponents date = DateUtils.getCurrentDateComponents();
        String currentDate = DateUtils.formatDate(date.day, date.month, date.year);
        binding.textStartDay.setText(currentDate);
        binding.textEndDate.setText(currentDate);
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

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

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

    private void validateDates() {
        String startDateStr = binding.textStartDay.getText().toString();
        String endDateStr = binding.textEndDate.getText().toString();

        Calendar startDate = parseDateToCalendar(startDateStr);
        Calendar endDate = parseDateToCalendar(endDateStr);

        if (startDate != null && endDate != null && startDate.after(endDate)) {
            binding.textEndDate.setText(startDateStr);
        }
    }

    private void navigateBack() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_main);
        navController.navigate(R.id.action_newLeaveAppFragment_to_requestHistoryFragment);
    }

    private class LoadHtmlTemplateTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                InputStream inputStream = requireActivity().getAssets().open("don_xin_nghi_phep.html");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                Log.e("NewLeaveAppFragment", "Error reading HTML template", e);
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            htmlTemplate = result;
            updateWebView();
        }
    }
    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}