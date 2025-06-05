package com.managerapp.personnelmanagerapp.presentation.decision.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import com.managerapp.personnelmanagerapp.data.remote.request.DecisionRequest;
import com.managerapp.personnelmanagerapp.databinding.FragmentCreateDecisionBinding;
import com.managerapp.personnelmanagerapp.domain.model.DecisionEnum;
import com.managerapp.personnelmanagerapp.domain.model.UserSummary;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.DecisionItem;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.DecisionItemAdapter;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.OptionItem;
import com.managerapp.personnelmanagerapp.presentation.decision.adapter.OptionItemAdapter;
import com.managerapp.personnelmanagerapp.presentation.decision.state.DecisionRequestBuilder;
import com.managerapp.personnelmanagerapp.presentation.decision.viewmodel.CreateDecisionViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.internal.aggregatedroot.codegen._com_managerapp_personnelmanagerapp_App;
import io.reactivex.rxjava3.subjects.PublishSubject;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.presentation.sendNotification.adapter.UserSearchRecyclerAdapter;
import com.managerapp.personnelmanagerapp.utils.DateUtils;

@AndroidEntryPoint
public class CreateDecisionFragment extends Fragment {
    private final String TAG = "CreateDecisionFragment";
    private FragmentCreateDecisionBinding binding;
    private CreateDecisionViewModel viewModel;
    private UserSearchRecyclerAdapter searchRecyclerAdapter;
    private OptionItemAdapter optionItemAdapter;
    private PublishSubject<String> searchSubject = PublishSubject.create();

    private UserSummary userSummary;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CreateDecisionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateDecisionBinding.inflate(inflater, container, false);
        setUpView();
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onListener();
        observeViewModel();
    }

    public void setUpView() {
        List<DecisionItem> genderItems = Arrays.asList(
                new DecisionItem(DecisionEnum.AWARD, R.string.decision_award),
                new DecisionItem(DecisionEnum.DISCIPLINE, R.string.decision_discipline),
                new DecisionItem(DecisionEnum.PROMOTION, R.string.decision_promotion),
                new DecisionItem(DecisionEnum.INCREASE_SALARY, R.string.decision_increase_salary),
                new DecisionItem(DecisionEnum.SENIORITY_ALLOWANCE, R.string.decision_seniority_allowance),
                new DecisionItem(DecisionEnum.TERMINATION, R.string.decision_termination)
        );
        DecisionItemAdapter adapter = new DecisionItemAdapter(requireContext(), genderItems);
        binding.decisionTypeSpinner.setAdapter(adapter);

        binding.textStartDay.setText(DateUtils.getCurrentDateAsString());
        binding.textEndDate.setText(DateUtils.getCurrentDateAsString());


        searchRecyclerAdapter = new UserSearchRecyclerAdapter(new ArrayList<>(), user -> {
            binding.employeeId.setText(String.valueOf(user.getId()));
            binding.employeeName.setText(user.getFullName());
            userSummary = user;
            searchRecyclerAdapter.updateData(Collections.emptyList());
        });
        binding.searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.searchResultRecyclerView.setAdapter(searchRecyclerAdapter);

        optionItemAdapter = new OptionItemAdapter(requireContext());
        binding.optionalSpinner.setAdapter(optionItemAdapter);
    }

    public void onListener() {
        setupClickListeners();
        setupSpinnerListeners();
        setupSearchListener();
    }

    private void setupClickListeners() {
        binding.backBtn.setOnClickListener(v -> navigateBack());
        binding.decisionDate.setOnClickListener(v -> showDatePickerDialog(true));
        binding.effectiveDate.setOnClickListener(v -> showDatePickerDialog(false));
        binding.submitBtn.setOnClickListener(v -> handleSubmitDecision());
    }

    private void navigateBack() {
        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack();
    }

    private void setupSpinnerListeners() {
        binding.decisionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleDecisionTypeSelection(parent, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void handleDecisionTypeSelection(AdapterView<?> parent, int position) {
        DecisionItem selectedItem = (DecisionItem) parent.getItemAtPosition(position);
        DecisionEnum selectedDecision = selectedItem.getDecisionEnum();
        Runnable handler = viewModel.decisionHandlers.get(selectedDecision);
        if (handler != null) {
            handler.run();
        }
    }

    private void setupSearchListener() {
        binding.searchUserEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSubject.onNext(s.toString());
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
        viewModel.search(searchSubject);
    }

    public void observeViewModel() {
        viewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Loading) {
                binding.progressBar.getRoot().setVisibility(View.VISIBLE);
            } else if (state instanceof UiState.Success) {
                Toast.makeText(requireContext(), "Tạo quyết định thành công", Toast.LENGTH_SHORT).show();
                navigateBack();
            } else if (state instanceof UiState.Error) {
                binding.progressBar.getRoot().setVisibility(View.INVISIBLE);
                Toast.makeText(requireContext(), ((UiState.Error) state).getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        viewModel.getSearchUiState().observe(getViewLifecycleOwner(), state -> {
            if (state.isLoading) {
                // Hiển thị loading nếu cần
            }

            if (state.userSummaryList != null && !state.userSummaryList.isEmpty()) {
                searchRecyclerAdapter.updateData(state.userSummaryList);
            } else {
                searchRecyclerAdapter.updateData(Collections.emptyList());
            }

            if (state.errorMessage != null) {
                Log.e(TAG, state.errorMessage);
                Toast.makeText(getContext(), state.errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getOptionalOptions().observe(getViewLifecycleOwner(), state -> {
            if (state != null) {
                binding.optionalSpinner.setVisibility(View.VISIBLE);
                optionItemAdapter.submitList(state);
                binding.optionalSpinner.setAdapter(optionItemAdapter);
            } else {
                binding.optionalSpinner.setVisibility(View.GONE);
            }
        });
    }


    private void handleSubmitDecision() {
        if (!validateInput()) return;

        DecisionRequest request = buildDecisionRequest();
        if (request != null) {
            viewModel.createDecision(request);
        }
    }

    private boolean validateInput() {
        if (binding.decisionTypeSpinner.getSelectedItem() == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn loại quyết định", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userSummary == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn nhân sự", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Thêm các validation khác
        return true;
    }

    private DecisionRequest buildDecisionRequest() {
        DecisionItem selectedItem = (DecisionItem) binding.decisionTypeSpinner.getSelectedItem();
        OptionItem selectedOption = (OptionItem) binding.optionalSpinner.getSelectedItem();

        String startDate = DateUtils.convertToApiDateFormat(binding.textStartDay.getText().toString());
        String endDate = DateUtils.convertToApiDateFormat(binding.textEndDate.getText().toString());

        DecisionRequestBuilder builder = new DecisionRequestBuilder()
                .setId(binding.decisionCode.getText().toString())
                .setValue(Double.valueOf(binding.decisionValue.getText().toString()))
                .setContent(binding.decisionContent.getText().toString())
                .setType(selectedItem.getDecisionEnum())
                .setDate(startDate)
                .setEffectiveDate(endDate)
                .setUserId(userSummary.getId());

        return applyDecisionSpecificOptions(builder, selectedItem.getDecisionEnum(), selectedOption);
    }

    private DecisionRequest applyDecisionSpecificOptions(DecisionRequestBuilder builder, DecisionEnum decisionType, OptionItem selectedOption) {
        switch (decisionType) {
            case INCREASE_SALARY:
                return builder.setSalaryPromotionId((Integer) selectedOption.getId()).build();

            case PROMOTION:
                return builder.setPositionId(String.valueOf(selectedOption.getId())).build();

            case SENIORITY_ALLOWANCE:
                return builder.setSeniorityAllowanceRuleId((Integer) selectedOption.getId()).build();

            default:
                return builder.build();
        }
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

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}