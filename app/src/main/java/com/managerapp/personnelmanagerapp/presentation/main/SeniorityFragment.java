package com.managerapp.personnelmanagerapp.presentation.main;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.managerapp.personnelmanagerapp.data.remote.response.UserProfileResponse;
import com.managerapp.personnelmanagerapp.databinding.FragmentSeniorityBinding;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.presentation.state.UiState;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSeniorityBinding.inflate(inflater, container, false);

        loadUserInfo();
//        setupRewardDisciplinePie();
//        setupRewardDisciplineBar();
        return binding.getRoot();
    }


    private void loadUserInfo() {
        UserProfileResponse user = ((MainActivity) requireActivity()).getCurrentUser();
        if (user != null) {
            binding.seniorityLeaveDayText.setText(user.getSeniorityLeaveDay() + "");
            binding.seniorityPercentageText.setText(user.getSeniorityPercentage() + "%");
            int totalAllowed = user.getServiceDuration() + user.getCarriedOverDay();
            int taken = user.getUsedLeaveDay();
            setupLeaveSummaryChart(totalAllowed, taken);
        } else {
            mainViewModel.loadUserAndRole();
            mainViewModel.getUiState().observe(getViewLifecycleOwner(), state -> {
                if (state instanceof UiState.Success) {
                    UserProfileResponse newUser = ((UiState.Success<UserProfileResponse>) state).getData();
                    ((MainActivity) requireActivity()).setUser(newUser);
                    binding.seniorityLeaveDayText.setText(newUser.getSeniorityLeaveDay() + "");
                    binding.seniorityPercentageText.setText(newUser.getSeniorityPercentage() + "%");
                    int totalAllowed = newUser.getNumDiscipline() + newUser.getCarriedOverDay();
                    int taken = newUser.getUsedLeaveDay();
                    setupLeaveSummaryChart(totalAllowed, taken);
                } else if (state instanceof UiState.Error) {
                    String errorMsg = ((UiState.Error) state).getErrorMessage();
                    Log.e(TAG, "Error loading profile: " + errorMsg);
                }
            });
        }
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

        // 👉 Hiển thị số ngày thay vì phần trăm
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
        chart.setUsePercentValues(false); // ❌ Tắt hiển thị %
        chart.getDescription().setEnabled(false);
        chart.animateY(1000, Easing.EaseInOutQuad);
        chart.invalidate();
    }
//
//    private void setupRewardDisciplinePie() {
//        PieChart chart = binding.pieChartRewardDiscipline;
//
//        List<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(4, "Khen thưởng"));
//        entries.add(new PieEntry(1, "Kỷ luật"));
//
//        PieDataSet dataSet = new PieDataSet(entries, "Khen thưởng & Kỷ luật");
//        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
//        dataSet.setValueTextSize(14f);
//        dataSet.setValueTextColor(Color.WHITE);
//
//        PieData pieData = new PieData(dataSet);
//        chart.setData(pieData);
//        chart.setDrawHoleEnabled(true);
//        chart.setHoleRadius(45f);
//        chart.setCenterText("Tổng cộng");
//        chart.setCenterTextSize(16f);
//        chart.setUsePercentValues(true);
//        chart.getDescription().setEnabled(false);
//        chart.animateY(1000);
//        chart.invalidate();
//    }
//    private void setupRewardDisciplineBar() {
//        BarChart chart = binding.barChartRewardDisciplineMonthly;
//
//        List<BarEntry> rewardEntries = new ArrayList<>();
//        List<BarEntry> disciplineEntries = new ArrayList<>();
//
//        for (int i = 1; i <= 6; i++) {
//            rewardEntries.add(new BarEntry(i, (float) (Math.random() * 3)));
//            disciplineEntries.add(new BarEntry(i, (float) (Math.random() * 2)));
//        }
//
//        BarDataSet rewardSet = new BarDataSet(rewardEntries, "Khen thưởng");
//        rewardSet.setColor(Color.rgb(76, 175, 80)); // green
//
//        BarDataSet disciplineSet = new BarDataSet(disciplineEntries, "Kỷ luật");
//        disciplineSet.setColor(Color.rgb(244, 67, 54)); // red
//
//        BarData barData = new BarData(rewardSet, disciplineSet);
//        barData.setBarWidth(0.4f); // width for each bar
//
//        chart.setData(barData);
//        chart.groupBars(0f, 0.2f, 0.05f); // group bars by month
//        chart.getDescription().setEnabled(false);
//        chart.animateY(1000);
//        chart.invalidate();
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng bộ nhớ tránh memory leak
    }

}
