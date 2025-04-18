package com.managerapp.personnelmanagerapp.presentation.fragments;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.databinding.FragmentScheduleBinding;

import java.util.ArrayList;
import java.util.List;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class ScheduleFragment extends BaseFragment {

    private FragmentScheduleBinding binding;

    public ScheduleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);

        setupLeaveSummaryChart();
//        setupRequestStatusChart();
        setupRewardDisciplinePie();
        setupRewardDisciplineBar();
        return binding.getRoot();
    }

    private void setupLeaveSummaryChart() {
        int totalAllowed = 12;
        int taken = 9;
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
//    private void setupRequestStatusChart() {
//        int approved = 5;
//        int pending = 2;
//        int rejected = 1;
//
//        List<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(approved, "Đã duyệt"));
//        entries.add(new PieEntry(pending, "Chờ duyệt"));
//        entries.add(new PieEntry(rejected, "Từ chối"));
//
//        PieDataSet dataSet = new PieDataSet(entries, "Trạng thái đơn");
//        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        dataSet.setValueTextSize(14f);
//        dataSet.setValueTextColor(Color.WHITE);
//
//        PieData pieData = new PieData(dataSet);
//
//        PieChart chart = binding.pieChartRequestStatus;
//        chart.setData(pieData);
//        chart.setUsePercentValues(true);
//        chart.setDrawHoleEnabled(false);
//        chart.getDescription().setEnabled(false);
//        chart.animateY(800, Easing.EaseOutBack);
//        chart.invalidate();
//    }

    private void setupRewardDisciplinePie() {
        PieChart chart = binding.pieChartRewardDiscipline;

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(4, "Khen thưởng"));
        entries.add(new PieEntry(1, "Kỷ luật"));

        PieDataSet dataSet = new PieDataSet(entries, "Khen thưởng & Kỷ luật");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData pieData = new PieData(dataSet);
        chart.setData(pieData);
        chart.setDrawHoleEnabled(true);
        chart.setHoleRadius(45f);
        chart.setCenterText("Tổng cộng");
        chart.setCenterTextSize(16f);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
        chart.invalidate();
    }
    private void setupRewardDisciplineBar() {
        BarChart chart = binding.barChartRewardDisciplineMonthly;

        List<BarEntry> rewardEntries = new ArrayList<>();
        List<BarEntry> disciplineEntries = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            rewardEntries.add(new BarEntry(i, (float) (Math.random() * 3)));
            disciplineEntries.add(new BarEntry(i, (float) (Math.random() * 2)));
        }

        BarDataSet rewardSet = new BarDataSet(rewardEntries, "Khen thưởng");
        rewardSet.setColor(Color.rgb(76, 175, 80)); // green

        BarDataSet disciplineSet = new BarDataSet(disciplineEntries, "Kỷ luật");
        disciplineSet.setColor(Color.rgb(244, 67, 54)); // red

        BarData barData = new BarData(rewardSet, disciplineSet);
        barData.setBarWidth(0.4f); // width for each bar

        chart.setData(barData);
        chart.groupBars(0f, 0.2f, 0.05f); // group bars by month
        chart.getDescription().setEnabled(false);
        chart.animateY(1000);
        chart.invalidate();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng bộ nhớ tránh memory leak
    }

}
