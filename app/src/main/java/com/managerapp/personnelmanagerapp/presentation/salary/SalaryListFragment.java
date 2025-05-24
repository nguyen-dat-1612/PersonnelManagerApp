package com.managerapp.personnelmanagerapp.presentation.salary;

import android.graphics.Color;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.managerapp.personnelmanagerapp.presentation.base.BaseFragment;
import com.managerapp.personnelmanagerapp.R;
import com.managerapp.personnelmanagerapp.databinding.FragmentSalaryListBinding;
import com.managerapp.personnelmanagerapp.domain.model.Salary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SalaryListFragment extends BaseFragment {
    private static final String TAG = "SalaryListFragment";
    private FragmentSalaryListBinding binding;
    private NavController navController;
    public SalaryListFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSalaryListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        view.post(() -> {
            try {
                navController = Navigation.findNavController(view);
            } catch (IllegalStateException e) {
                Log.e(TAG, "NavController chưa sẵn sàng", e);
            }
        });
        List<Salary> salaryList = new ArrayList<>();

        setupSalaryChart();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            salaryList.add(new Salary("1", "E001", "C001", "2024-01", 500, 50, 100, 150, 350, sdf.parse("2024-02-01"), Salary.PaymentStatus.PAID));
            salaryList.add(new Salary("2", "E002", "C002", "2024-01", 600, 40, 120, 160, 440, sdf.parse("2024-02-02"), Salary.PaymentStatus.PENDING));
            salaryList.add(new Salary("3", "E003", "C003", "2024-01", 550, 30, 110, 140, 410, sdf.parse("2024-02-03"), Salary.PaymentStatus.PAID));
            salaryList.add(new Salary("4", "E004", "C004", "2024-01", 700, 20, 140, 160, 540, sdf.parse("2024-02-04"), Salary.PaymentStatus.PAID));
            salaryList.add(new Salary("5", "E005", "C005", "2024-01", 450, 25, 90, 115, 335, sdf.parse("2024-02-05"), Salary.PaymentStatus.PENDING));
            salaryList.add(new Salary("6", "E006", "C006", "2024-01", 800, 10, 160, 170, 630, sdf.parse("2024-02-06"), Salary.PaymentStatus.PAID));
            salaryList.add(new Salary("7", "E007", "C007", "2024-01", 750, 15, 150, 165, 585, sdf.parse("2024-02-07"), Salary.PaymentStatus.PAID));
            salaryList.add(new Salary("8", "E008", "C008", "2024-01", 680, 35, 130, 165, 515, sdf.parse("2024-02-08"), Salary.PaymentStatus.PENDING));
            salaryList.add(new Salary("9", "E009", "C009", "2024-01", 620, 45, 125, 170, 480, sdf.parse("2024-02-09"), Salary.PaymentStatus.PAID));
            salaryList.add(new Salary("10", "E010", "C010", "2024-01", 580, 20, 115, 135, 445, sdf.parse("2024-02-10"), Salary.PaymentStatus.PENDING));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        binding.backBtn.setOnClickListener(v -> {
            requireActivity().finish();
        });


        binding.recyclerViewSalary.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewSalary.setAdapter(new SalaryAdapter(salaryList,id -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("id",id);
//            navController.navigate(R.id.action_salaryListFragment_to_salaryDetailFragment, bundle);
        }));
        return view;
    }

    private void setupSalaryChart() {
        LineChart chart = binding.lineChartSalary;

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 8.5f));
        entries.add(new Entry(2, 8.7f));
        entries.add(new Entry(3, 9.2f));
        entries.add(new Entry(4, 9.0f));
        entries.add(new Entry(5, 9.5f));
        entries.add(new Entry(6, 10.2f));

        LineDataSet dataSet = new LineDataSet(entries, "Lương (triệu)");
        dataSet.setColor(Color.BLUE);
        dataSet.setLineWidth(2f);
        dataSet.setValueTextSize(12f);
        dataSet.setCircleColor(Color.RED);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.CYAN);

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.getDescription().setEnabled(false);
        chart.animateX(1000);
        chart.invalidate();
    }
}