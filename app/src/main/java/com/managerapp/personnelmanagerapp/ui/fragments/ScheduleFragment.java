package com.managerapp.personnelmanagerapp.ui.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.managerapp.personnelmanagerapp.databinding.FragmentScheduleBinding;
import com.managerapp.personnelmanagerapp.ui.adapters.DateAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleFragment extends Fragment {

    private DateAdapter dateAdapter;
    private FragmentScheduleBinding binding;

    public ScheduleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout với View Binding
        binding = FragmentScheduleBinding.inflate(inflater, container, false);

        binding.rvDates.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        // Lấy danh sách ngày trong tuần
        List<Integer> dateList = getCurrentWeekDates();
        dateAdapter = new DateAdapter(requireContext(), dateList);
        binding.rvDates.setAdapter(dateAdapter);

        // Lấy ngày hôm nay
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_MONTH);

        // Cập nhật ngày được chọn và cuộn đến đó
        dateAdapter.setSelectedDate(today);
        int todayPosition = dateList.indexOf(today);
        if (todayPosition != -1) {
            binding.rvDates.scrollToPosition(todayPosition);
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Giải phóng bộ nhớ tránh memory leak
    }

    private List<Integer> getCurrentWeekDates() {
        List<Integer> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // Đưa về đầu tuần (Thứ 2)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        for (int i = 0; i < 7; i++) {
            dates.add(calendar.get(Calendar.DAY_OF_MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }
}
