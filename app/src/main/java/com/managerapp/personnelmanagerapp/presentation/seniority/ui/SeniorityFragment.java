package com.managerapp.personnelmanagerapp.presentation.seniority.ui;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentSeniorityBinding;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.main.viewmodel.MainViewModel;
import com.managerapp.personnelmanagerapp.presentation.main.state.UiState;
import java.util.ArrayList;
import java.util.List;

public class SeniorityFragment extends BaseFragment {
    private FragmentSeniorityBinding binding;
    private MainViewModel mainViewModel;
    private final String TAG = "SeniorityFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.loadUserAndRole();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSeniorityBinding.inflate(inflater, container, false);

        loadUserInfo();
        return binding.getRoot();
    }


    private void loadUserInfo() {
        mainViewModel.loadUserAndRole();
        mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof UiState.Success) {
                UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                if (newUser != null) {
                    binding.seniorityLeaveDayText.setText(newUser.getSeniorityLeaveDay() + "");
                    binding.seniorityPercentageText.setText(newUser.getSeniorityPercentage() + "%");
                    int totalAllowed = newUser.getNumDiscipline() + newUser.getCarriedOverDay();
                    int taken = newUser.getUsedLeaveDay();
                    setupLeaveSummaryChart(totalAllowed, taken);
                }
            } else if (state instanceof UiState.Error) {
                String errorMsg = ((UiState.Error) state).getErrorMessage();
                Log.e(TAG, "Error loading profile: " + errorMsg);
            }
        });
    }

    private void setupLeaveSummaryChart(int totalAllowed, int taken) {
        int remaining = totalAllowed - taken;

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(taken, "Đã nghỉ"));
        entries.add(new PieEntry(remaining, "Còn lại"));

        PieDataSet dataSet = new PieDataSet(entries, "Tổng ngày phép");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setSliceSpace(3f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + " ngày";
            }
        });

        PieData pieData = new PieData(dataSet);

        PieChart chart = binding.pieChartLeaveSummary;
        chart.setData(pieData);
        chart.setDrawHoleEnabled(true);
        chart.setHoleRadius(50f);
        chart.setTransparentCircleRadius(55f);
        chart.setCenterText("Ngày nghỉ\n" + taken + "/" + totalAllowed + " đã dùng");
        chart.setCenterTextSize(16f);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000, Easing.EaseInOutQuad);
        chart.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
